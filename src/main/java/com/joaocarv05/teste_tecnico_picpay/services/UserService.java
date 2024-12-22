package com.joaocarv05.teste_tecnico_picpay.services;

import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import com.joaocarv05.teste_tecnico_picpay.domain.user.UserType;
import com.joaocarv05.teste_tecnico_picpay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User saveUser(User user){
        return userRepository.save(user);
    }
    public User findUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public UserDetails findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * Verifica se uma transação pode ou não ser efetuada com base no saldo do usuário e na quantia a ser transferida.
     * @param payer agente que realiza a transferência
     * @param amount quantia a ser transferida
     */
    public void validateUserTransfer(User payer, BigDecimal amount) {
        if (payer.getBalance().compareTo(amount) < 0){
            throw new RuntimeException("Saldo insuficiente");
        }
        if (payer.getUserType() == UserType.MERCHANT){
            throw new RuntimeException("Usuário do tipo lojista não pode efetuar transações");
        }
    }

}
