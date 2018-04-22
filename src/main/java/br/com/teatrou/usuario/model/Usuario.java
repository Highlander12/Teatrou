package br.com.teatrou.usuario.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.teatrou.permissao.model.Permissao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
public @Data @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor class Usuario {
	
	@Id
	private Long codigo;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String senha;
	
	@NotNull
	private String email;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuasrio_permissao", joinColumns = @JoinColumn(name="codigo_usuario"),
	inverseJoinColumns = @JoinColumn(name="codigo_permissao"))
	List<Permissao> permissoes;
	
	
	
}
