package br.com.teatrou.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.teatrou.exception.CompraInexistenteException;
import br.com.teatrou.exception.IngressoInexistenteException;
import br.com.teatrou.exception.UsuarioInexistenteOuDeslogadoException;
import br.com.teatrou.model.Compra;
import br.com.teatrou.model.Evento;
import br.com.teatrou.model.Ingresso;
import br.com.teatrou.model.Usuario;
import br.com.teatrou.model.dto.CompraDTO;
import br.com.teatrou.model.enums.FaixaEtariaEnum;
import br.com.teatrou.model.enums.SituacaoEnum;
import br.com.teatrou.model.enums.StatusEnum;
import br.com.teatrou.repository.CompraRepository;
import br.com.teatrou.repository.EventoRepository;
import br.com.teatrou.repository.IngressoRepository;
import br.com.teatrou.token.AuthenticationHelper;

@Service
public class CompraService {

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private IngressoRepository ingressoRepository;

	@Autowired
	private AuthenticationHelper authenticationHelper;

	private Integer quantidadeTotalIngressos;

	/**
	 * <p>
	 *  Método que registra a compra como pendente, reservando os ingressos até que o pagamento seja aprovado,
	 *  ou cancelado.
	 * </p>
	 * @param compraDTO
	 * @param chaveUnica
	 * @return compra 
	 */
	public Compra registrarCompraPendente(CompraDTO compraDTO, String chaveUnica) {
		
		// Pega usuário logado
		Usuario usuario = authenticationHelper.getUsuario();
		if(usuario == null) 
			throw new UsuarioInexistenteOuDeslogadoException();
		// Cria compra
		Compra compra = new Compra();
		compra.setQuantidadeIngresso(compraDTO.getIngressosInteira() + compraDTO.getIngressosMeia());
		compra.setUsuario(usuario);
		compra.setDataCompra(LocalDate.now());
		compra.setCodigo(chaveUnica);
		// Busca evento relacionado
		Evento evento = eventoRepository.findByUsuario(usuario);
		compra.setValorTotal(getValorTotal(compraDTO, evento));
		compra.setSituacao(SituacaoEnum.PENDENTE);
		// Salva a compra
		Compra compraSave = compraRepository.save(compra);
        // Salva os ingresso de acordo com a faixa etaria
		quantidadeTotalIngressos = evento.getQuantidadeIngresso();
		salvarIngresso(evento, compraSave, compraDTO.getIngressosInteira().intValue(), FaixaEtariaEnum.INTEIRA, StatusEnum.PENDENTE);
		salvarIngresso(evento, compraSave, compraDTO.getIngressosMeia().intValue(), FaixaEtariaEnum.MEIA, StatusEnum.PENDENTE);

		return compra;
	}
	
	/**
	 * <p>
	 *  Busca as compras do usuário logado;
	 * </p>
	 * @param pageable
	 * @return
	 */
	public Page<Compra> buscarCompras(Pageable pageable) {
		Usuario usuario = authenticationHelper.getUsuario();
		return compraRepository.findByUsuario(usuario, pageable);
	}
	
	/**
	 * <p>
	 *   Altera a situação da compra, caso o pagamento seja aprovado ou cancelado.
	 * </p>
	 * @param codigo
	 * @param situacaoEnum
	 */
	public void alteraCompra(Long codigo, SituacaoEnum situacaoEnum ) {
		Compra compra = compraRepository.findOne(codigo);
		if( compra == null) 
			throw new CompraInexistenteException();
		compra.setSituacao(situacaoEnum);
		
		compraRepository.save(compra);
	}
	
	/**
	 * <p>
	 *   Altera o status do ingresso, caso o pagamento seja aprovado ou cancelado.
	 * </p>
	 * @param codigo
	 * @param status
	 */
	public void alteraIngresso(String codigo, StatusEnum status ) {
		Ingresso ingresso = ingressoRepository.findOne(Long.parseLong(codigo));
		if( ingresso == null) 
			throw new IngressoInexistenteException();
		
		ingresso.setStatus(status);
		
		ingressoRepository.save(ingresso);
	}

	
	/**
	 * <p>
	 * Método que salva os ingressos relacionado a uma compra.
	 * </p>
	 * @param evento
	 * @param compraSave
	 * @param quantidade
	 * @param faixaEtaria
	 * @param status
	 */
	private void salvarIngresso(Evento evento, Compra compraSave, Integer quantidade, FaixaEtariaEnum faixaEtaria, StatusEnum status) {
		if (quantidadeTotalIngressos > quantidade) {
			for (int i = 0; i < quantidade; i++) {
				ingressoRepository.save(criaIngresso(compraSave, evento, faixaEtaria, status));
				quantidadeTotalIngressos--;
			}
		}
	}

	/**
	 * <p>
	 *  Método que pega o valor total da compra.
	 * </p>
	 * @param compraDTO
	 * @param evento
	 * @return valorTotal
	 */
	private BigDecimal getValorTotal(CompraDTO compraDTO, Evento evento) {
		return BigDecimal.valueOf((compraDTO.getIngressosInteira() * evento.getValorIngresso().doubleValue())
				+ (compraDTO.getIngressosMeia() * (evento.getValorIngresso().doubleValue() / 2)));
	}

	
	/**
	 * <p>
	 *  Método que cria a instancia dos ingressos.
	 * </p
	 * @param compra
	 * @param evento
	 * @param faixaEtaria
	 * @param status
	 * @return ingresso
	 */
	private Ingresso criaIngresso(Compra compra, Evento evento, FaixaEtariaEnum faixaEtaria, StatusEnum status) {
		Ingresso ingresso = new Ingresso();
		ingresso.setCompra(compra);
		ingresso.setFaixaEtaria(faixaEtaria);
		ingresso.setEvento(evento);
		ingresso.setStatus(status);
		return ingresso;
	}

}
