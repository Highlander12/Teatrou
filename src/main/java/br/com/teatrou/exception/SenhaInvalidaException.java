package br.com.teatrou.exception;

public class SenhaInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SenhaInvalidaException(String msg) {
		super(msg);
	}
}
