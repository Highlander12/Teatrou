package br.com.teatrou.token;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.teatrou.model.Usuario;
import br.com.teatrou.security.UsuarioSistema;

@Component
public class AuthenticationHelper {

	private Boolean fromExt;

	public UsuarioSistema getUser() {
		return (UsuarioSistema) ((TeatrouAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
				.getPrincipal();
	}

	public Usuario getUsuario() {
		return ((TeatrouAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
				.getUsuario();
	}

	public void setFromExt() {
		this.fromExt = true;
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
	}

	public Boolean isFromExt() {
		return fromExt;
	}

}
