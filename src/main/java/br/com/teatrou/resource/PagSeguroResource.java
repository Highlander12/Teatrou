package br.com.teatrou.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.teatrou.model.dto.CompraDTO;
import br.com.teatrou.service.PagSeguroService;

@RestController
@RequestMapping(value = "/pag-seguro")
public class PagSeguroResource {
	
	@Autowired
	private PagSeguroService pagSeguroService;


	/**
	 * <p>
	 * Método que cria uma transação e gera um link onde o usuario realizara o pagamento.
	 * @param compra
	 * @return link da transação para pagto.
	 */
	@PostMapping(value = "/pagamento", produces = "application/json")
	public ResponseEntity<String> geraLinkPagamento(@Valid @RequestBody CompraDTO compra) {
		return new ResponseEntity<>(pagSeguroService.criarPagamento(compra), HttpStatus.OK);
	}

	/**
	 * <p> 
	 *  Método que escuta as notificações das transações criadas.
	 * </p>
	 * @param nCode objeto padrão do PagSeguro não alterar
	 * @param nType objeto padrão do PagSeguro não alterar
	 * @return {@link verificaStatus}.
	 */
	@PostMapping(value = "/notificacao")
	public ResponseEntity<String> registrarNotificacao(
			@RequestParam(value = "notificationCode") String nCode,
			@RequestParam(value = "notificationType") String nType) {
		return new ResponseEntity<>(pagSeguroService.verificaStatus(nCode, nType), HttpStatus.OK);
	}
		
}
