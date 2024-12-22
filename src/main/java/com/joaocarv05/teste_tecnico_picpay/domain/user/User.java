package com.joaocarv05.teste_tecnico_picpay.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
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

    public User(UserDTO userDTO, String encryptedPassword) {
        this.name = userDTO.name();
        this.CPF = userDTO.CPF();
        this.email = userDTO.email();
        this.password = encryptedPassword;
        this.balance = userDTO.balance();
        this.userType = UserType.valueOf(userDTO.user_type());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userType.name()));
    }


    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
