package com.joaocarv05.teste_tecnico_picpay.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "CPF")
    String CPF;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;
    @Column(name = "balance")
    BigDecimal balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    UserType userType;

    public User(UserDTO userDTO) {
        this.name = userDTO.name();
        this.CPF = userDTO.CPF();
        this.email = userDTO.email();
        this.password = userDTO.password();
        this.balance = userDTO.balance();
        this.userType = UserType.valueOf(userDTO.user_type());
    }
}
