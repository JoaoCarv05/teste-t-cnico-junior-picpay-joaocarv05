package com.joaocarv05.teste_tecnico_picpay.controllers;

import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import com.joaocarv05.teste_tecnico_picpay.domain.user.UserDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.joaocarv05.teste_tecnico_picpay.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO){
        User user = userService.createUser(userDTO);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<User> getUser(Long id){
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }
}
