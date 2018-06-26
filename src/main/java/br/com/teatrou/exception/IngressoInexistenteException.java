package br.com.teatrou.exception;

public class IngressoInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IngressoInexistenteException() {
		super("Operacao não permitida compra inexistente.");
	}
}
