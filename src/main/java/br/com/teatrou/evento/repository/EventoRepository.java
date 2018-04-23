package br.com.teatrou.evento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teatrou.evento.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

}
