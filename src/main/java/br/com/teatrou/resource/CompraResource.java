package br.com.teatrou.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teatrou.model.Compra;
import br.com.teatrou.model.dto.CompraDTO;
import br.com.teatrou.repository.CompraRepository;
import br.com.teatrou.repository.filter.CompraFilter;
import br.com.teatrou.service.CompraService;

@RestController
@RequestMapping(value = "/compras")
public class CompraResource {

	@Autowired
	private CompraService compraService;
	
	@Autowired
	private CompraRepository compraRepository;

//	@PostMapping
//	@PreAuthorize("hasAuthority('ROLE_REALIZAR_COMPRA')")
//	public ResponseEntity<Compra> comprar(@Valid @RequestBody CompraDTO compra) {
//		return new ResponseEntity<>(compraService.comprar(compra), HttpStatus.CREATED);
//	}

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_COMPRA')")
	public ResponseEntity<Page<Compra>> buscarCompras(CompraFilter compraFilter, Pageable pageable) {
		return new ResponseEntity<>(compraRepository.filtrar(compraFilter, pageable), HttpStatus.OK);
	}

}
