package exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException (String e) {
        super(e);
    }
}