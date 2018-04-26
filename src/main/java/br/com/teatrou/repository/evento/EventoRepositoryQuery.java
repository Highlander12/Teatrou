package br.com.teatrou.repository.evento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.teatrou.model.Evento;
import br.com.teatrou.repository.filter.EventoFilter;

public interface EventoRepositoryQuery {
	
	public Page<Evento> filtrar(EventoFilter eventoFilter, Pageable pageable);

}
