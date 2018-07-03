package br.com.teatrou.service;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.teatrou.exception.EventoInexistenteException;
import br.com.teatrou.exception.UsuarioInexistenteOuDeslogadoException;
import br.com.teatrou.model.Evento;
import br.com.teatrou.model.Usuario;
import br.com.teatrou.repository.EventoRepository;
import br.com.teatrou.repository.UsuarioRepository;
import br.com.teatrou.storage.S3;
import br.com.teatrou.token.AuthenticationHelper;

@Service
public class EventoService {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private S3 s3;

	@Autowired
	private AuthenticationHelper authenticationHelper;

	
	/**
	 * <p>
	 *  Método que lista todos eventos
	 * </p>
	 * @param pageable
	 * @return eventos
	 */
	public Page<Evento> listar(Pageable pageable) {
		return eventoRepository.findAll(pageable);
	}
	
	/** 
	 * <p>
	 * Método que salva um evento, e remove a regra de exclusão do anexo na amazon caso o anexo no {@link evento } 
	 * passado na assinatura o método seja valido.
	 * </p>
	 * @param evento
	 * @return evento criado
	 */
	public Evento salvar(Evento evento) {

		Long codUsuario = evento.getUsuario().getCodigo();
		Usuario usuario = usuarioRepository.findByCodigo(codUsuario);
		
		if (usuario == null) {
			throw new UsuarioInexistenteOuDeslogadoException();
		}
		if (StringUtils.hasText(evento.getAnexo())) {
			evento.setUrlAnexo(s3.configurarUrl(evento.getAnexo()));
			s3.salvar(evento.getAnexo());
		}

		return eventoRepository.save(evento);
	}

	/** 
	 * <p>
	 * Método que valida se o usuário esta deslogado ou e inexistente
	 * </p>
	 * @param evento
	 */
	private void validUsuario(Evento evento) {
		Usuario usuario = null;
		Long codigo = authenticationHelper.getUsuario().getCodigo();
		if (codigo != null) {
			usuario = usuarioRepository.findOne(codigo);
		}
		if (usuario == null) {
			throw new UsuarioInexistenteOuDeslogadoException();
		}
	}

	/**
	 * <p>
	 * Método que atualiza um evento, remove o anexo ou substitui dependendo da estado do evento.
	 * </p>
	 * @param codigo
	 * @param evento
	 * @return evento atualizado
	 */
	public Evento atualizar(Long codigo, Evento evento) {
		Evento eventoSalvo = buscaPeloCodigo(codigo);
		if (!evento.getUsuario().equals(eventoSalvo.getUsuario())) {
			validUsuario(evento);
		}
		if (StringUtils.isEmpty(evento.getAnexo()) &&
			StringUtils.hasText(eventoSalvo.getAnexo())) {
			s3.remover(eventoSalvo.getAnexo());
		} 
		else if (StringUtils.hasText(evento.getAnexo()) &&
				    !evento.getAnexo().equals(eventoSalvo.getAnexo())) {
			
			s3.substituir(eventoSalvo.getAnexo(), evento.getAnexo());
		}

		BeanUtils.copyProperties(evento, eventoSalvo, "codigo");
		return salvar(eventoSalvo);
	}

	
	/**
	 * 
	 * @param codigo
	 * @return evento específico
	 */
	private Evento buscaPeloCodigo(Long codigo) {
		Evento eventoSalvo = eventoRepository.findOne(codigo);
		if (eventoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return eventoSalvo;
	}

	
	/**
	 * <p>
	 * Método que atualiza a data de um evento
	 * </p>
	 * @param codigo
	 * @param dataEvento
	 * @return
	 */
	public Evento atualizarDataEvento(Long codigo, LocalDate dataEvento) {
		Evento eventoSalvo = buscaPeloCodigo(codigo);
		eventoSalvo.setDataEvento(dataEvento);
		return salvar(eventoSalvo);
	}

	/**
	 * <p>
	 * Método que ativa um evento
	 * </p>
	 * @param codigo
	 * @return evento ativado
	 */
	public Evento ativarEvento(Long codigo) {
		Evento eventoSalvo = buscaPeloCodigo(codigo);
		eventoSalvo.setAtivo(true);
		return salvar(eventoSalvo);
	}

	/**
	 * <p>
	 * Método que atualiza uma descrição
	 * </p>
	 * @param codigo
	 * @param descricao
	 * @return evento atualizado
	 */
	public Evento atualizarDescricao(Long codigo, String descricao) {
		Evento eventoSalvo = buscaPeloCodigo(codigo);
		eventoSalvo.setDescricao(descricao);
		return salvar(eventoSalvo);
	}

	/**
	 * <p>
	 * Método que retoma os ingresso salvos, que o pagamento foi cancelado, disponibilizando a compra deles
	 *  novamente.
	 * </p>
	 * @param codigo
	 */
	public void retomaIngresso(String codigo) {
		Evento evento = buscaPeloCodigo(Long.parseLong(codigo));
		if(evento == null) 
			throw new EventoInexistenteException();
		evento.setQuantidadeIngresso(evento.getQuantidadeIngresso() + 1);
		eventoRepository.save(evento);
	}
	


}
