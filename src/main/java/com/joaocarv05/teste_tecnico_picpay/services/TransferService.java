package com.joaocarv05.teste_tecnico_picpay.services;

import com.joaocarv05.teste_tecnico_picpay.domain.transfer.Transfer;
import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import com.joaocarv05.teste_tecnico_picpay.domain.user.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joaocarv05.teste_tecnico_picpay.repositories.TransferRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransferService {
    TransferRepository transferRepository;
    UserService userService;

    @Autowired
    public TransferService(TransferRepository transferRepository, UserService userService) {
        this.transferRepository = transferRepository;
        this.userService = userService;
    }

    public Boolean TransferAuthorization(User payer, User payee, BigDecimal amount) {
        return payer.getBalance().compareTo(amount) >= 0 && payer.getUserType() != UserType.MERCHANT;
    }

    public Transfer saveTransfer(Transfer transferDTO){
        return transferRepository.save(transferDTO);
    }

    public Transfer getTransfer(Transfer transferDTO){
        Optional<Transfer> transfer = transferRepository.findById(transferDTO.getId());
        if (transfer.isPresent()) {
            return transfer.get();
        } else throw new RuntimeException("transferência não encontrada");
    }

}
