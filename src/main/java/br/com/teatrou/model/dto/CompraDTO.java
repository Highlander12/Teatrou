package br.com.teatrou.model.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor @AllArgsConstructor class CompraDTO {
 
	@NotNull(message = "Informe o codigo do evento")
	private Long codigoEvento;
	
	@NotNull(message = "Informe a quantidade ingressos com a faixa etaria meia")
	private Integer ingressosMeia;
	
	@NotNull(message = "Informe a quantidade ingressos com a faixa etaria inteira")
	private Integer ingressosInteira;
	
	@NotNull(message = "O usuario não foi passado")
	private Long codigoUsuario;
	
}
