package br.com.teatrou.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teatrou.model.Compra;
import br.com.teatrou.model.Usuario;
import br.com.teatrou.repository.compra.CompraRepositoryQuery;

@Repository
public interface CompraRepository extends  JpaRepository<Compra, String>, CompraRepositoryQuery{

	Page<Compra> findByUsuario(Usuario usuario, Pageable pageable);

}
