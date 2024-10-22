package exceptions;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException() {
        super("Não foi possível realizar está transação");
    }
}
