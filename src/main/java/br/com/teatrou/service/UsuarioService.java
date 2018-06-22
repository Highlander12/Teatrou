package br.com.teatrou.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.teatrou.exception.EmailInvalidoException;
import br.com.teatrou.exception.SenhaInvalidaException;
import br.com.teatrou.model.Usuario;
import br.com.teatrou.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	

	public Usuario buscaPeloCodigo(Long codigo) {
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
		if(!validaPassword(usuario.getSenha()))
			throw new SenhaInvalidaException("Digite uma senha valida.");
		if(!validaEmail(usuario.getEmail()))
			throw new EmailInvalidoException("Digite um Email valido.");
		usuario.setSenha(enconder(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}
	

	public Usuario atualizar(Long codigo, Usuario usuario) {
		Usuario usuarioSalvo = buscaPeloCodigo(codigo);
		BeanUtils.copyProperties(usuario, usuarioSalvo, "codigo");
		return salvar(usuario);
	}
	
	private String enconder (String senha) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	private Boolean validaPassword(final String password) {
	    Pattern p = Pattern.compile("^(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?])(?=\\S+$).{8,}$");
	    Matcher m = p.matcher(password);
	return m.matches();
	}
	
	private Boolean validaEmail(final String email) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(email);
		return m.matches();
	}
}
