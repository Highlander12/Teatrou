package br.com.teatrou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teatrou.model.Ingresso;


@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {

}
