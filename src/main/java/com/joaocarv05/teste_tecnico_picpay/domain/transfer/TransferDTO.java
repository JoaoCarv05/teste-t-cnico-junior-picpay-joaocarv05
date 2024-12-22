package com.joaocarv05.teste_tecnico_picpay.domain.transfer;

import java.math.BigDecimal;

public record TransferDTO(BigDecimal value, Long payer, Long payee) {
}
