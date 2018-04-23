package br.com.teatrou.ingresso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teatrou.ingresso.model.Ingresso;


@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

}
