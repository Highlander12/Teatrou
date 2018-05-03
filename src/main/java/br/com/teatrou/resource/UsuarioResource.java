package br.com.teatrou.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teatrou.model.Usuario;
import br.com.teatrou.repository.UsuarioRepository;
import br.com.teatrou.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository UsuarioRepository;
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario){
		return new ResponseEntity<Usuario>(usuarioService.salvar(usuario), HttpStatus.CREATED);
	}
	
	public ResponseEntity<Usuario> buscar(@PathVariable(required = true) Long codigo){
		Usuario usuario = UsuarioRepository.findOne(codigo);
		return usuario == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(usuario);
	}
	
	public ResponseEntity<Usuario> alterar(@PathVariable(required = true) Long codigo,
			@Valid @RequestBody Usuario usuario){
		return new ResponseEntity<>(usuarioService.atualizar(codigo, usuario), HttpStatus.OK);
	}

}
