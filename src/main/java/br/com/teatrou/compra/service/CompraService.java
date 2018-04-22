package br.com.teatrou.compra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teatrou.compra.repository.CompraRepository;

@Service
public class CompraService {
	
	
	@Autowired
	private CompraRepository compraRepository;
	
	

}
