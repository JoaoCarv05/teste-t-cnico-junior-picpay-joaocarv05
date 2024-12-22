package com.joaocarv05.teste_tecnico_picpay.domain.transfer;

import com.joaocarv05.teste_tecnico_picpay.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Entity
@Table(name = "transfer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transfer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "date")
    ZonedDateTime date;
    @Column(name = "amount")
    BigDecimal amount;
    @JoinColumn(name = "payee")
    @OneToOne
    User payee;
    @JoinColumn(name = "payer")
    @OneToOne
    User payer;
}
