package br.com.teatrou.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teatrou.model.Compra;
import br.com.teatrou.repository.CompraRepository;
import br.com.teatrou.repository.filter.CompraFilter;

@RestController
@RequestMapping(value = "/compras")
public class CompraResource {
	
	@Autowired
	private CompraRepository compraRepository;

	/**
	 * <p>
	 * Método que busca as compras do usuario entre periodos, ou so lista todas.
	 * @param compraFilter
	 * @param pageable
	 * @return compras.
	 */
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_COMPRA')")
	public ResponseEntity<Page<Compra>> buscarCompras(CompraFilter compraFilter, Pageable pageable) {
		return new ResponseEntity<>(compraRepository.filtrar(compraFilter, pageable), HttpStatus.OK);
	}

}
