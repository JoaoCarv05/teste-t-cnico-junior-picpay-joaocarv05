package com.joaocarv05.teste_tecnico_picpay.controllers;

import com.joaocarv05.teste_tecnico_picpay.domain.user.AuthenticationDTO;
import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import com.joaocarv05.teste_tecnico_picpay.domain.user.UserDTO;
import com.joaocarv05.teste_tecnico_picpay.infra.security.TokenService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.joaocarv05.teste_tecnico_picpay.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    TokenService tokenService;
    AuthenticationManager authenticationManager;
    UserService userService;

    @Autowired
    public UserController(TokenService tokenService, AuthenticationManager authenticationManager, UserService userService) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) throws JOSEException {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok(tokenService.sign((User) authentication.getPrincipal()));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
        if (userService.findUserByEmail(userDTO.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(userDTO.password());
        System.out.println(encryptedPassword);
        User user = new User(userDTO, encryptedPassword);
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }
}
