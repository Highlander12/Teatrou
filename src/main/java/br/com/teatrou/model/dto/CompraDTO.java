package br.com.teatrou.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor @AllArgsConstructor class CompraDTO {
 
	private Long codigoEvento;
	private Integer ingressosMeia;
	private Integer ingressosInteira;
	
}
