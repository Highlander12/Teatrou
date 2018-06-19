package br.com.teatrou.exception;

public class CompraInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CompraInexistenteException() {
		super("Operacao não permitida compra inexistente.");
	}
}
