package br.com.teatrou.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teatrou.model.Compra;

@Repository
public interface CompraRepository extends  JpaRepository<Compra, Long>{

}
