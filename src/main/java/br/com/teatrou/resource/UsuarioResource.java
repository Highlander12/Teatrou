package br.com.teatrou.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	private UsuarioRepository usuarioRepository;

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO')")
	public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario) {
		return new ResponseEntity<Usuario>(usuarioService.salvar(usuario), HttpStatus.CREATED);
	}

	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO')")
	public ResponseEntity<Usuario> buscar(@PathVariable(required = true) Long codigo) {
		Usuario usuario = usuarioRepository.findOne(codigo);
		return usuario == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(usuario);
	}

	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_USUARIO')")
	public ResponseEntity<Usuario> alterar(@PathVariable(required = true) Long codigo,
			@Valid @RequestBody Usuario usuario) {
		return new ResponseEntity<>(usuarioService.atualizar(codigo, usuario), HttpStatus.OK);
	}

	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_EXCLUIR_USUARIO')")
	public ResponseEntity<Usuario> deletar(@PathVariable(required = true) Long codigo) {
		usuarioRepository.delete(codigo);
		return ResponseEntity.noContent().build();
	}

}
