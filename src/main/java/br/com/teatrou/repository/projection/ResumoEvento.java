package br.com.teatrou.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

public @Data class ResumoEvento {

	private Long codigo;
	private String usuario;
	private String anexo;
	private String titulo;
	private String descricao;
	private LocalDate dataEvento;
	private String horaInicial;
	private String horaFinal;
	private String tema;
	private String endereco;
	private Integer quantidadeIngresso;
	private BigDecimal valorIngresso;
	private Boolean ativo;
	private String urlAnexo;

	public ResumoEvento(Long codigo, String usuario, String anexo, String titulo, String descricao,
			LocalDate dataEvento, String horaInicial, String horaFinal, String tema, String endereco,
			Integer quantidadeIngresso, BigDecimal valorIngresso, Boolean ativo) {
		this.codigo = codigo;
		this.usuario = usuario;
		this.anexo = anexo;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataEvento = dataEvento;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
		this.tema = tema;
		this.endereco = endereco;
		this.quantidadeIngresso = quantidadeIngresso;
		this.valorIngresso = valorIngresso;
		this.ativo = ativo;
	}

}
