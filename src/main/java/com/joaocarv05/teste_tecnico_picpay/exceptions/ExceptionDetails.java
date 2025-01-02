package com.joaocarv05.teste_tecnico_picpay.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ExceptionDetails {
    private int code;
    private String message;
    private LocalDateTime timestamp;
}
