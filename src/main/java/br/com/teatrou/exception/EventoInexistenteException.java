package br.com.teatrou.exception;

public class EventoInexistenteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EventoInexistenteException() {
		super("Operacao não permitida evento inexistente.");
	}
}
