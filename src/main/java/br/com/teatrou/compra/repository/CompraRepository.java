package br.com.teatrou.compra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teatrou.compra.model.Compra;

@Repository
public interface CompraRepository extends  JpaRepository<Compra, Long>{

}
