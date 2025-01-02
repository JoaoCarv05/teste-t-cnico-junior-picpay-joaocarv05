package com.joaocarv05.teste_tecnico_picpay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionDetails handleNullPointerException(NullPointerException exception){
        return new ExceptionDetails(500, exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionDetails handleAccessDeniedException(AccessDeniedException exception){
        return new ExceptionDetails(403, exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(TransactionAuthorizationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDetails handleAuthorizationException(TransactionAuthorizationException exception){
        return new ExceptionDetails(401, exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDetails handleBadCredentialsException(BadCredentialsException exception){
        return new ExceptionDetails(401, exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionDetails handleAuthenticationException(AuthenticationException exception){
        return new ExceptionDetails(401, exception.getMessage(), LocalDateTime.now());
    }
}
