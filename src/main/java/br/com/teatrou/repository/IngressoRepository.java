package br.com.teatrou.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teatrou.model.Compra;
import br.com.teatrou.model.Ingresso;
import br.com.teatrou.model.Usuario;
import br.com.teatrou.model.enums.FaixaEtariaEnum;


@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
	
	List<Ingresso> findByCompraAndFaixaEtaria(Compra compra, FaixaEtariaEnum faixaEtariaEnum);


}
