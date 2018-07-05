package br.com.teatrou.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.teatrou.model.Compra;
import br.com.teatrou.model.Evento;
import br.com.teatrou.model.Ingresso;
import br.com.teatrou.model.enums.FaixaEtariaEnum;


@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, String> {
	
	List<Ingresso> findByCompraAndFaixaEtaria(Compra compra, FaixaEtariaEnum faixaEtariaEnum);

	List<Ingresso> findByEventoAndCompra(Evento evento, Compra compra);


}
