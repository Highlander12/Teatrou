package br.com.teatrou.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teatrou.config.property.TeatrouApiProperty;
import br.com.teatrou.exception.UsuarioInexistenteOuDeslogadoException;
import br.com.teatrou.model.Compra;
import br.com.teatrou.model.Ingresso;
import br.com.teatrou.model.Usuario;
import br.com.teatrou.model.dto.CompraDTO;
import br.com.teatrou.model.enums.FaixaEtariaEnum;
import br.com.teatrou.model.enums.SituacaoEnum;
import br.com.teatrou.model.enums.StatusEnum;
import br.com.teatrou.repository.IngressoRepository;
import br.com.teatrou.token.AuthenticationHelper;
import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Credentials;
import br.com.uol.pagseguro.domain.Item;
import br.com.uol.pagseguro.domain.PaymentRequest;
import br.com.uol.pagseguro.domain.Sender;
import br.com.uol.pagseguro.domain.Transaction;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.TransactionStatus;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.service.NotificationService;

@Service
public class PagSeguroService {

	@Autowired
	private AuthenticationHelper authenticationHelper;

	@Autowired
	private TeatrouApiProperty property;

	@Autowired
	private CompraService compraService;
	
	@Autowired
	private EventoService eventoService;

	@Autowired
	private IngressoRepository ingressoRepository;

	
	/**
	 * <p> Método responsável pela criação do link de pagamento, envio dos ingressos para o PagSeguro
	 * e criação da compra com o status pendente e dos ingressos.
	 * </p>
	 * 
	 * @param compraDTO objeto que representa a compra, onde o possui o codigo do evento e a quantidade
	 * de ingresso, pois o usuario e pego pela classe {@link AuthenticationHelper } deis que ele esteja 
	 * logado e o projeto esteja no perfil de Produção.
	 * @return link para pagamento
	 */
	public String criarPagamento(CompraDTO compraDTO) {
		
		try {
			String chaveUnica = gerarIdUnico(compraDTO);
			PaymentRequest request = new PaymentRequest();
			// Id do Pagamento
			request.setReference(chaveUnica);
			request.setCurrency(Currency.BRL);
			// Dados do Usuario
			request.setSender(getSender());
			// Items do carrinho
			request.setItems(gerarItems(compraDTO, chaveUnica));
			// URL para que o PagSeguro ira chamar;
			request.setNotificationURL(property.getOrigin() + "/pag-seguro/notificacao");
			// URL que o PagSeguro ira redirecionar ao aprovar o pagamento
			request.setRedirectURL(property.getOriginPermitida() + "/compra/pagamento-finalizado");
			return request.register(getCredentials());
			
		} catch (PagSeguroServiceException e) {
			
			Logger.getLogger(PagSeguroService.class.getName()).log(Level.SEVERE, null, e);
			return e.getMessage();
		}
	}

	/**
	 * <p> Método que o PagSeguro chama para informar o andamento do Pagamento criado pelo método
	 * {@link criarPagamento } , para os 3 status a seguir um ação para cada um e realizada, persistindo
	 * a informação no banco, e finalizando a compra e aprovando os ingresso.
	 * </p>
	 * @param nCode
	 * @param nType
	 * @return status da compra
	 */
	public String verificaStatus(String nCode, String nType) {
		
		try {
			// Pega a transação de pagamento 
			Transaction transaction = NotificationService.checkTransaction(getCredentials(), nCode);

			// Status Aguardando Pagamento
			if (TransactionStatus.WAITING_PAYMENT.equals(transaction.getStatus())) {
				registrarStatus(transaction, SituacaoEnum.AGUARDANDO_PAGAMENTO);
			}
			// Status Pago
			else if (TransactionStatus.PAID.equals(transaction.getStatus())) {
				registrarStatus(transaction, SituacaoEnum.PAGAMENTO_APROVADO);
			}
			// Status Cancelado
			else if (TransactionStatus.CANCELLED.equals(transaction.getStatus())) {
				registrarStatus(transaction, SituacaoEnum.PAGAMENTO_CANCELADO);
			}
			
			return String.valueOf(transaction.getStatus());
			
		} catch (PagSeguroServiceException e) {
			
			Logger.getLogger(PagSeguroService.class.getName()).log(Level.SEVERE, null, e);
			return e.getMessage();
		}
	}

	
	/**
	 * <p> Método que altera os status da compra e do ingresso de fato, e caso o pagamento seja cancelado
	 * retoma a quantidade de ingressos para o Evento, deixando disponivel novamente a quantidade, que antes 
	 * estava no carinho.
	 * </p>
	 * @param transaction
	 * @param situacao
	 */
	private void registrarStatus(Transaction transaction, SituacaoEnum situacao) {
		// Muda o status da compra
		compraService.alteraCompra(Long.parseLong(transaction.getReference()), situacao);
		
		// Pega todos itens, no caso os ingressos
		List<Item> items = transaction.getItems();
		
		// Muda o status dos ingressos
		if(SituacaoEnum.PAGAMENTO_APROVADO.equals(situacao)) {
			items.forEach(ingresso -> {
				compraService.alteraIngresso(ingresso.getId(), StatusEnum.APROVADO);
			});
		} 
		else if (SituacaoEnum.PAGAMENTO_CANCELADO.equals(situacao)) {
			items.forEach(ingresso -> {
				compraService.alteraIngresso(ingresso.getId(), StatusEnum.CANCELADO);
				eventoService.retomaIngresso(ingresso.getId());
			});
			
		}
	}

	private List<Item> gerarItems(CompraDTO compraDTO, String chave) {
		Compra compra = compraService.registrarCompraPendente(compraDTO, chave);
		List<Ingresso> ingressosInteira = ingressoRepository.findByCompraAndFaixaEtaria(compra,
				FaixaEtariaEnum.INTEIRA);
		List<Ingresso> ingressosMeia = ingressoRepository.findByCompraAndFaixaEtaria(compra, FaixaEtariaEnum.MEIA);

		List<Item> items = new ArrayList<Item>();

		ingressosInteira.forEach(ingressoInteira -> items.add(criarItem(ingressoInteira)));
		ingressosMeia.forEach(ingressoMeia -> items.add(criarItem(ingressoMeia)));

		return items;
	}

	private Item criarItem(Ingresso ingresso) {
		Item item = new Item();
		item.setId(ingresso.getCodigo().toString());
		item.setDescription(
				"INGRESSO EVENTO - " + ingresso.getFaixaEtaria() + " - EVENTO " + ingresso.getEvento().getTitulo());
		item.setQuantity(1);
		item.setAmount(ingresso.getEvento().getValorIngresso());
		return item;
	}

	private Sender getSender() {
		Usuario usuario = authenticationHelper.getUsuario();
		if (usuario == null)
			throw new UsuarioInexistenteOuDeslogadoException();
		return new Sender(usuario.getEmail(), usuario.getNome());
	}

	private String gerarIdUnico(CompraDTO compraDTO) {
		return UUID.randomUUID().toString() + "_" + compraDTO.getCodigoEvento();
	}

	private Credentials getCredentials() throws PagSeguroServiceException {
		return new AccountCredentials(property.getPagSeguro().getEmail(), property.getPagSeguro().getToken());
	}

	
}
