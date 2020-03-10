package code.project.springbootjwt.security.jwt.exception;

public class TokenValidationException extends Exception {

    private static final long serialVersionUID = 8919884466824716182L;

    public TokenValidationException() {
    }

    public TokenValidationException(String message) {
        super(message);
    }

    public TokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
