package com.joaocarv05.teste_tecnico_picpay.domain.user;

import java.math.BigDecimal;

public record UserDTO(String name, String email, String CPF, String password, BigDecimal balance, String user_type) {
}
