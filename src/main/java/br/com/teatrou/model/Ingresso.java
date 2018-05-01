package br.com.teatrou.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.teatrou.model.enums.FaixaEtariaEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingresso")
public @Data @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor class Ingresso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@ManyToOne
	@JoinColumn(name = "codigo_evento")
	private Evento evento;
	
	@ManyToOne
	@JoinColumn(name = "codigo_compra")
	private Compra compra;

	@Enumerated(EnumType.STRING)
	@Column(name = "faixa_etaria")
	private FaixaEtariaEnum faixaEtaria;

	@NotNull
	private @Getter(AccessLevel.NONE) Boolean ativo;
	
	

}
