package br.com.teatrou.evento.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.teatrou.usuario.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "evento")
public @Data @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor class Evento {
	
	@Id
	private Long codigo;
	
	@NotNull(message = "Codigo do usuario é obrigatorio.")
	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;
	
	@NotNull
	private String titulo;
	
	@NotNull
	private String descricao;
	
	@NotNull
	private LocalDate dataEvento;
	
	@NotNull
	private String horaInicial;
	
	@NotNull
	private String horaFinal;
	
	@NotNull
	private String tema;
	
	@NotNull
	private String endereco;
	
	@NotNull
	private Integer quantidadeIngresso;

	@NotNull
	private BigDecimal valorIngresso;
	
	
}
