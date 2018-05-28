package br.com.teatrou.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.teatrou.model.Usuario;
import br.com.teatrou.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	public Usuario findByCodigo(Long codigo) {
		Usuario usuario = usuarioRepository.findOne(codigo);
		if (usuario == null) {
			throw new IllegalArgumentException();
		}
		return usuario;
	}
	
	public Page<Usuario> listar(Pageable pageable){
		return usuarioRepository.findAll(pageable);
	}
	
	public Usuario salvar(Usuario usuario) {
		usuario.setSenha(enconder(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	
	public Usuario atualizar(Long codigo, Usuario usuario) {
		Usuario usuarioSalvo = findByCodigo(codigo);
		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");
		return salvar(usuario);
	}
	
	public String enconder (String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
}
