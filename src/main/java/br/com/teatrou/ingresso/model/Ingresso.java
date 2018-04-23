package br.com.teatrou.ingresso.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.teatrou.compra.model.Compra;
import br.com.teatrou.evento.model.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingresso")
public @Data @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor class Ingresso {

	@Id
	private Long codigo;

	@NotNull(message = "Codigo da evento é obrigatorio.")
	@ManyToOne
	@JoinColumn(name = "codigo_evento")
	private Evento evento;
	
	@NotNull(message = "Codigo da evento é obrigatorio.")
	@ManyToOne
	@JoinColumn(name = "codigo_evento")
	private Compra compra;

	@NotNull
	@Enumerated(EnumType.STRING)
	private FaixaEtariaEnum faixaEtaria;

}
