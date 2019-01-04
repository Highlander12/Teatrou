package br.com.teatrou.model.dto;

import javax.validation.constraints.NotNull;

import br.com.teatrou.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

public @Getter @AllArgsConstructor class UsuarioDTO extends Usuario {

	@NotNull
	private Boolean productor;
	@NotNull
	private String confirmacaoSenha;


}
