package br.com.teatrou.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.teatrou.model.enums.SituacaoEnum;
import lombok.Data;

public @Data class CompraFilter {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataEventoDe;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataEventoAte;

	private Long codigoUsuario;

	private SituacaoEnum situacao;

	private Long codigo;
}
