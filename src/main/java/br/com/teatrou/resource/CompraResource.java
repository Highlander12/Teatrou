package br.com.teatrou.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teatrou.service.CompraService;

@RestController
@RequestMapping(value = "/compra")
public class CompraResource {
	
	
	@Autowired
	private CompraService compraService;
	
	

}
