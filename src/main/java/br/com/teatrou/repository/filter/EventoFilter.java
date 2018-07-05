package br.com.teatrou.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

public @Data class EventoFilter {
	
	private Boolean ativo;
	
	private String descricao;
	
	private String tema;
	
	private String titulo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataEventoDe;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataEventoAte;

}
