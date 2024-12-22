package com.joaocarv05.teste_tecnico_picpay.services;

import com.joaocarv05.teste_tecnico_picpay.domain.authorization.AuthorizationResponse;
import com.joaocarv05.teste_tecnico_picpay.domain.transfer.Transfer;
import com.joaocarv05.teste_tecnico_picpay.domain.transfer.TransferDTO;
import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.joaocarv05.teste_tecnico_picpay.repositories.TransferRepository;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;

@Service
public class TransferService {
    TransferRepository transferRepository;
    UserService userService;
    RestTemplate restTemplate;

    @Autowired
    public TransferService(TransferRepository transferRepository, UserService userService, RestTemplate restTemplate) {
        this.transferRepository = transferRepository;
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    /**
     * este metodo é responsavel por implementar a lógica de criação de uma
     * transação, atualizando o saldo dos usuários e persistindo os dados
     * da transação.
     * @param transferDTO objeto de transferência de dados de uma transação entre usuários
     * @return Transfer
     */
    @Transactional
    public Transfer createTransferService(TransferDTO transferDTO) {
        User payer = userService.findUserById(transferDTO.payer());
        User payee = userService.findUserById(transferDTO.payee());

        userService.validateUserTransfer(payer, transferDTO.value());

        assert authorizeTransaction() : "transação não autorizada";

        Transfer transfer = persistTransfer(transferDTO, payee, payer);
        setNewUserBalanceAndSave(transferDTO, payer, payee);

        return transfer;
    }

    private Transfer persistTransfer(TransferDTO transferDTO, User payee, User payer) {
        Transfer transfer = Transfer.builder().date(ZonedDateTime.now())
                .amount(transferDTO.value())
                .payee(payee)
                .payer(payer)
                .build();
        transferRepository.save(transfer);
        return transfer;
    }

    private void setNewUserBalanceAndSave(TransferDTO transferDTO, User payer, User payee) {
        payer.setBalance(payer.getBalance().subtract(transferDTO.value()));
        payee.setBalance(payee.getBalance().add(transferDTO.value()));
        userService.saveUser(payer);
        userService.saveUser(payee);
    }

    public Transfer getTransfer(Transfer transferDTO) {
        return this.transferRepository.findById(transferDTO.getId()).orElseThrow(() -> new RuntimeException("transferência não encontrada"));
    }

    /**
     * Faz uma requisição a um serviço tercerizado para verificar se a transação está autorizada
     * @return falso caso a transação não seja autorizada, do contrário retorna verdadeiro
     */
    public Boolean authorizeTransaction() {
        ResponseEntity<AuthorizationResponse> authorizationResponse = new RestTemplate().getForEntity("https://util.devi.tools/api/v2/authorize", AuthorizationResponse.class);

        return authorizationResponse.getStatusCode() == HttpStatus.OK &&
                authorizationResponse.getBody().data().authorization().equals("true");
    }


}
