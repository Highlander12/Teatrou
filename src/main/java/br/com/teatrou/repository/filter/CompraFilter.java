package br.com.teatrou.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.teatrou.model.Usuario;
import lombok.Data;

public @Data class CompraFilter {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataEventoDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataEventoAte;
	
	private Usuario usuario;
}
