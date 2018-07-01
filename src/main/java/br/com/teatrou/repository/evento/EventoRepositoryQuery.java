package br.com.teatrou.repository.evento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.teatrou.repository.filter.EventoFilter;
import br.com.teatrou.repository.projection.ResumoEvento;

public interface EventoRepositoryQuery {
	
	public Page<ResumoEvento> filtrar(EventoFilter eventoFilter, Pageable pageable);

}
