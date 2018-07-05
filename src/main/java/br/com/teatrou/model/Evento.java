package br.com.teatrou.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.teatrou.storage.S3;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "evento")
@Component
public @Data @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor class Evento {
	
	@JsonIgnore
	@Transient
	private static S3 s3;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull(message = "Codigo do usuario é obrigatorio.")
	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;
	
	private String anexo;
	
	@Transient
	private String urlAnexo;
	
	@NotNull
	private String titulo;
	
	@NotNull
	private String descricao;
	
	@NotNull
	@Column(name = "data_evento")
	private LocalDate dataEvento;
	
	@NotNull
	@Column(name = "hora_inicial")
	private String horaInicial;
	
	@NotNull
	@Column(name = "hora_final")
	private String horaFinal;
	
	@NotNull
	private String tema;
	
	@NotNull
	private String endereco;
	
	@NotNull
	@Column(name = "quantidade_ingresso")
	private Integer quantidadeIngresso;

	@NotNull
	@Column(name = "valor_ingresso")
	private BigDecimal valorIngresso;
	
	@NotNull
	private @Getter(AccessLevel.NONE) Boolean ativo;
	
	@Autowired
	public void setS3(S3 s3) {
		this.s3 = s3;
	}
	
	
	@PostLoad
	public void postLoad() {
		if(StringUtils.hasText(this.getAnexo())) {
			this.urlAnexo =  s3.configurarUrl(this.anexo);
					
		}
	}
	
}
