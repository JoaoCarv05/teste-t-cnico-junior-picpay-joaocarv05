package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.TransferRepository;
import domain.transfer.Transfer;
import domain.user.User;
import domain.user.UserType;

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
