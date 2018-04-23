package br.com.teatrou.permissao.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="permissao")
public @Data @EqualsAndHashCode class Permissao {
	
	@Id
	private Long codigo;
	
	@NotNull
	private String descricao;
	

}
