package com.joaocarv05.teste_tecnico_picpay.services;

import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import com.joaocarv05.teste_tecnico_picpay.repositories.UserRepository;
import com.joaocarv05.teste_tecnico_picpay.domain.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(UserDTO userDTO){
        return userRepository.save(new User(userDTO));
    }
    public User findUserById(Long id){
        Optional<User> rawUser = userRepository.findById(id);
        if (rawUser.isPresent()){
            return rawUser.get();
        }else throw new RuntimeException("Usuário não encontrado");
    }
}
