package br.com.teatrou.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teatrou.config.property.TeatrouApiProperty;

@RestController
@RequestMapping(value = "/pag-seguro")
public class PagSeguroResource {
	
	
	@Autowired
	private TeatrouApiProperty property;
	
	

}
