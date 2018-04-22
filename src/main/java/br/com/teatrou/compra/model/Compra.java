package br.com.teatrou.compra.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
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
@Table(name = "compra")
public @Data @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor class  Compra {

	
	@Id
	private Long codigo;
	
    @NotNull(message = "Codigo da usuario é obrigatorio.")
	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;
	
	@NotNull
	@Column(name = "quantidade_ingresso")
	private Integer quantidadeIngresso;
	
	@NotNull
	@Column(name = "data_compra")
	private LocalDate dataCompra;
	
	@NotNull
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
		
}
