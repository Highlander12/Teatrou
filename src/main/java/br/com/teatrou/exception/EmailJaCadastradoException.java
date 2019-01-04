package br.com.teatrou.exception;

public class EmailJaCadastradoException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailJaCadastradoException() {
		super("Operacao não permitida email em uso.");
	}
}
