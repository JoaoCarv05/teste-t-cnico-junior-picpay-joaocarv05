package com.joaocarv05.teste_tecnico_picpay.exceptions;

public class TransactionAuthorizationException extends RuntimeException{
    public TransactionAuthorizationException(String message) {
        super(message);
    }

    public TransactionAuthorizationException() {
        super("Não foi possível realizar está transação");
    }
}
