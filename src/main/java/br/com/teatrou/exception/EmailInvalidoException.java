package br.com.teatrou.exception;

public class EmailInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailInvalidoException(String msg) {
		super(msg);
	}
}
