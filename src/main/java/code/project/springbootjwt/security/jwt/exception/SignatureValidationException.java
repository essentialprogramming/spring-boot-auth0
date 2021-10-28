package code.project.springbootjwt.security.jwt.exception;


public class SignatureValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SignatureValidationException() {
	}

	public SignatureValidationException(String msg) {
		super(msg);
	}

	public SignatureValidationException(String message, Throwable cause) {
		super(message, cause);
	}



}