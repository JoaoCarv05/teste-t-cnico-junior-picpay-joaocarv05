package user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "name")
    String name;
    @Column(name = "CPF")
    String CPF;
    @Column(name = "email")
    String Email;
    @Column(name = "password")
    String password;
    @Column(name = "balance")
    BigDecimal balance;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    UserType userType;
}
