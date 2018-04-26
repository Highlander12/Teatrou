package br.com.teatrou.repository;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teatrou.model.Evento;
import br.com.teatrou.repository.evento.EventoRepositoryQuery;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long>, EventoRepositoryQuery {

	Page<Evento> findAll(Pageable pageable);

}
