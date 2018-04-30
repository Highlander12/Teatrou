package br.com.teatrou.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teatrou.model.Compra;
import br.com.teatrou.model.dto.CompraDTO;
import br.com.teatrou.service.CompraService;

@RestController
@RequestMapping(value = "/compra")
public class CompraResource {
	
	
	@Autowired
	private CompraService compraService;
	
	@PostMapping
	public ResponseEntity<Compra> comprar(@Valid @RequestBody CompraDTO compra) {
		return new ResponseEntity<Compra>(compraService.comprar(compra), HttpStatus.CREATED);
	}
	

}
