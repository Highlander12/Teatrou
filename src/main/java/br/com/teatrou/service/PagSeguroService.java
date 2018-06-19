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
import br.com.teatrou.repository.EventoRepository;
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
	private EventoRepository eventoRepository;

	@Autowired
	private AuthenticationHelper authenticationHelper;

	@Autowired
	private TeatrouApiProperty property;

	@Autowired
	private CompraService compraService;

	@Autowired
	private IngressoRepository ingressoRepository;

	public String criarPagamento(CompraDTO compraDTO) {
		try {
			PaymentRequest request = new PaymentRequest();
			String chaveUnica = gerarIdUnico(compraDTO);
			request.setReference(chaveUnica);
			request.setCurrency(Currency.BRL);
			request.setSender(getSender());
			request.setItems(gerarItems(compraDTO, chaveUnica));
			request.setNotificationURL(property.getOrigin() + "/pag-seguro/notificacao");
			request.setRedirectURL(property.getOriginPermitida() + "/compra/pagamento-finalizado");

			return request.register(getCredentials());
		} catch (PagSeguroServiceException e) {
			Logger.getLogger(PagSeguroService.class.getName()).log(Level.SEVERE, null, e);
			return e.getMessage();
		}
	}

	public String verificaStatus(String nCode, String nType) {
		try {
			Transaction transaction = NotificationService.checkTransaction(getCredentials(), nCode);

			if (TransactionStatus.PAID.equals(transaction.getStatus())) {
				registrarStatus(transaction, SituacaoEnum.PAGAMENTO_FINALIZADO);
			} else if (TransactionStatus.CANCELLED.equals(transaction.getStatus())) {
				registrarStatus(transaction, SituacaoEnum.PAGAMENTO_CANCELADO);
			}
			return transaction.getStatus().toString();
		} catch (PagSeguroServiceException e) {
			Logger.getLogger(PagSeguroService.class.getName()).log(Level.SEVERE, null, e);
			return e.getMessage();
		}
	}

	private void registrarStatus(Transaction transaction, SituacaoEnum situacaoEnum) {
		compraService.alteraCompra(Long.parseLong(transaction.getReference()), situacaoEnum);
		
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
