package br.com.teatrou.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teatrou.config.property.TeatrouApiProperty;
import br.com.teatrou.model.dto.CompraDTO;
import br.com.teatrou.service.PagSeguroService;

@RestController
@RequestMapping(value = "/pag-seguro")
public class PagSeguroResource {
	
	
	@Autowired
	private PagSeguroService pagSeguroService;
	
	
	@PostMapping(value = "/pagamento", produces = "application/json")
	public ResponseEntity<String> geraLinkPagamento(@Valid @RequestBody CompraDTO compra) {
		
	
	    return null;
	}
}
