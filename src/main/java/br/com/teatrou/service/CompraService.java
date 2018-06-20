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

	public Compra registrarCompraPendente(CompraDTO compraDTO, String codigo) {
		Usuario usuario = authenticationHelper.getUsuario();
		if(usuario == null) 
			throw new UsuarioInexistenteOuDeslogadoException();
		
		Compra compra = new Compra();
		compra.setQuantidadeIngresso(compraDTO.getIngressosInteira() + compraDTO.getIngressosMeia());
		compra.setUsuario(usuario);
		compra.setDataCompra(LocalDate.now());
		compra.setCodigo(Long.parseLong(codigo));
		
		Evento evento = eventoRepository.findByUsuario(usuario);
		Double valorTotal = getValorTotal(compraDTO, evento);
		compra.setValorTotal(BigDecimal.valueOf(valorTotal));
		compra.setSituacao(SituacaoEnum.PENDENTE);
		Compra compraSave = compraRepository.save(compra);

		quantidadeTotalIngressos = evento.getQuantidadeIngresso();
		salvarIngresso(evento, compraSave, compraDTO.getIngressosInteira().intValue(), FaixaEtariaEnum.INTEIRA, StatusEnum.PENDENTE);
		salvarIngresso(evento, compraSave, compraDTO.getIngressosMeia().intValue(), FaixaEtariaEnum.MEIA, StatusEnum.PENDENTE);

		return compra;
	}
	
	public Page<Compra> buscarCompras(Pageable pageable) {
		Usuario usuario = authenticationHelper.getUsuario();
		return compraRepository.findByUsuario(usuario, pageable);
	}
	
	public void alteraCompra(Long codigo, SituacaoEnum situacaoEnum ) {
		Compra compra = compraRepository.findOne(codigo);
		if( compra == null) 
			throw new CompraInexistenteException();
		compra.setSituacao(situacaoEnum);
		
		compraRepository.save(compra);
	}
	
	public void alteraIngresso(String codigo, StatusEnum status ) {
		Ingresso ingresso = ingressoRepository.findOne(Long.parseLong(codigo));
		if( ingresso == null) 
			throw new IngressoInexistenteException();
		
		ingresso.setStatus(status);
		
		ingressoRepository.save(ingresso);
	}

	private void salvarIngresso(Evento evento, Compra compraSave, Integer quantidade, FaixaEtariaEnum faixaEtaria, StatusEnum status) {
		if (quantidadeTotalIngressos > quantidade) {
			for (int i = 0; i < quantidade; i++) {
				ingressoRepository.save(criaIngresso(compraSave, evento, faixaEtaria, status));
				quantidadeTotalIngressos--;
			}
		}
	}

	private double getValorTotal(CompraDTO compraDTO, Evento evento) {
		return (compraDTO.getIngressosInteira() * evento.getValorIngresso().doubleValue())
				+ (compraDTO.getIngressosMeia() * (evento.getValorIngresso().doubleValue() / 2));
	}

	private Ingresso criaIngresso(Compra compra, Evento evento, FaixaEtariaEnum faixaEtaria, StatusEnum status) {
		Ingresso ingresso = new Ingresso();
		ingresso.setCompra(compra);
		ingresso.setFaixaEtaria(faixaEtaria);
		ingresso.setEvento(evento);
		ingresso.setStatus(status);
		return ingresso;
	}

}
