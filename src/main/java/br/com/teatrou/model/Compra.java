package br.com.teatrou.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.teatrou.model.enums.SituacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "compra")
public @Data @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor class  Compra {


	@Id
	private String codigo;

	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;

	@Column(name = "quantidade_ingresso")
	private Integer quantidadeIngresso;

	@Column(name = "data_compra")
	private LocalDate dataCompra;

	@Column(name = "valor_total")
	private BigDecimal valorTotal;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private SituacaoEnum situacao;

}
