package br.com.teatrou.resource;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.teatrou.model.Evento;
import br.com.teatrou.model.dto.AnexoDTO;
import br.com.teatrou.repository.EventoRepository;
import br.com.teatrou.repository.filter.EventoFilter;
import br.com.teatrou.service.EventoService;
import br.com.teatrou.storage.S3;

@RestController
@RequestMapping(value = "/evento")
public class EventoResource {

	@Autowired
	private EventoService eventoService;

	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private S3 s3;

	
	/**
	 * <p>
	 * Método utilizado para upload de uma foto de apresentação de um Evento.
	 * </p>
	 * @param arquivo
	 * @return um anexo com nome, e url para acesso na amazon.
	 */
	@PostMapping("/image")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_EVENTO')")
	public AnexoDTO uploadImage(@RequestParam MultipartFile arquivo) {
		String nome =  s3.salvarTemporariamente(arquivo);
		return new AnexoDTO(nome, s3.configurarUrl(nome));
	}
	
	/**
	 * <p>
	 *  Método que filtra os eventos, pelo tema, titulo, descrição e período;
	 * </p>
	 * @param eventoFilter
	 * @param pageable
	 * @return eventos
	 */
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EVENTO')")
	public ResponseEntity<Page<Evento>> filtrar(EventoFilter eventoFilter, Pageable pageable) {
		return new ResponseEntity<>(eventoRepository.filtrar(eventoFilter, pageable), HttpStatus.OK);
	}

	
	/**
	 * <p>
	 * Método que cria um Evento.
	 * </p>
	 * @param evento
	 * @return o Evento criado
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_EVENTO')")
	public ResponseEntity<Evento> salvar(@Valid @RequestBody Evento evento) {
		return new ResponseEntity<Evento>(eventoService.salvar(evento), HttpStatus.CREATED);
	}

	/**
	 * <p>
	 *	 Busca um Evento específico.
	 * </p>
	 * @param codigo
	 * @return
	 */
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_EVENTO')")
	public ResponseEntity<Evento> buscar(@PathVariable(required = true) Long codigo) {
		Evento evento = eventoRepository.findOne(codigo);
		return evento == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(evento);
	}

	/**
	 * <p>
	 *   Deleta um Evento em específico
	 * </p>
	 * 
	 * @param codigo
	 * @return
	 */
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_EXCLUIR_EVENTO')")
	public ResponseEntity<Evento> delete(@PathVariable(required = true) Long codigo) {
		eventoRepository.delete(codigo);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	
	/**
	 * <p>
	 *  Atualiza um Evento
	 * </p>
	 * @param codigo
	 * @param evento
	 * @return evento atualizado
	 */
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_EVENTO')")
	public ResponseEntity<Evento> alterar(@PathVariable(required = true) Long codigo,
			@Valid @RequestBody Evento evento) {
		return new ResponseEntity<>(eventoService.atualizar(codigo, evento), HttpStatus.OK);
	}

	
	/**
	 * <p>
	 *   Atualiza a data do Evento
	 * </p>
	 * @param codigo
	 * @param dataEvento
	 * @return evento atualizado
	 */
	@PutMapping("/{codigo}/data-pagamento")
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_EVENTO')")
	public ResponseEntity<Evento> alterarDataEvento(@PathVariable(required = true) Long codigo,
			@RequestBody LocalDate dataEvento) {
		return new ResponseEntity<>(eventoService.atualizarDataEvento(codigo, dataEvento), HttpStatus.OK);
	}
	
	/**
	 * <p>
	 *   Ativa um Evento
	 * </p>
	 * @param codigo
	 * @param dataEvento
	 * @return evento atualizado
	 */
	@PutMapping("/{codigo}/ativar")
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_EVENTO')")
	public ResponseEntity<Evento> ativarEvento(@PathVariable(required = true) Long codigo) {
		return new ResponseEntity<>(eventoService.ativarEvento(codigo), HttpStatus.OK);
	}

	
	/**
	 * <p>
	 *   Atualiza a descrição de um evento
	 * </p>
	 * @param codigo
	 * @param dataEvento
	 * @return evento atualizado
	 */
	@PutMapping("/{codigo}/descricao")
	@PreAuthorize("hasAuthority('ROLE_ALTERAR_EVENTO')")
	public ResponseEntity<Evento> alterarDescricao(@PathVariable(required = true) Long codigo,
			@Valid @RequestBody String descricao) {
		return new ResponseEntity<>(eventoService.atualizarDescricao(codigo, descricao), HttpStatus.OK);
	}

}
