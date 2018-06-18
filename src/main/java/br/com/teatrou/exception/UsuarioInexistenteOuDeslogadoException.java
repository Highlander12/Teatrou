package br.com.teatrou.exception;

public class UsuarioInexistenteOuDeslogadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioInexistenteOuDeslogadoException() {
		super("Operacao não permitida usuario inexistente ou deslogado.");
	}
}
