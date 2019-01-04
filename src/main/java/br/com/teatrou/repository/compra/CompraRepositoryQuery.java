package br.com.teatrou.repository.compra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.teatrou.model.Compra;
import br.com.teatrou.repository.filter.CompraFilter;

public interface CompraRepositoryQuery {

	public Page<Compra> filtrar(CompraFilter compraFilter, Pageable pageable);

}
