package br.com.teatrou.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.teatrou.model.Compra;
import br.com.teatrou.model.Evento;
import br.com.teatrou.model.Ingresso;
import br.com.teatrou.model.Usuario;
import br.com.teatrou.model.dto.CompraDTO;
import br.com.teatrou.model.enums.FaixaEtariaEnum;
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

	public Compra comprar(CompraDTO compraDTO) {
		Usuario usuario = authenticationHelper.getUsuario();
		Compra compra = new Compra();

		compra.setQuantidadeIngresso(compraDTO.getIngressosInteira() + compraDTO.getIngressosMeia());
		compra.setUsuario(usuario);
		compra.setDataCompra(LocalDate.now());
		Evento evento = eventoRepository.findByUsuario(usuario);
		Double valorTotal = getValorTotal(compraDTO, evento);
		compra.setValorTotal(BigDecimal.valueOf(valorTotal));
		Compra compraSave = compraRepository.save(compra);

		quantidadeTotalIngressos = evento.getQuantidadeIngresso();
		salvarIngresso(evento, compraSave, compraDTO.getIngressosInteira().intValue(), FaixaEtariaEnum.INTEIRA);
		salvarIngresso(evento, compraSave, compraDTO.getIngressosMeia().intValue(), FaixaEtariaEnum.MEIA);

		return compra;
	}

	private void salvarIngresso(Evento evento, Compra compraSave, Integer quantidade, FaixaEtariaEnum faixaEtariaEnum) {
		if (quantidadeTotalIngressos > quantidade) {
			for (int i = 0; i < quantidade; i++) {
				ingressoRepository.save(criaIngresso(compraSave, evento, faixaEtariaEnum));
				quantidadeTotalIngressos--;
			}
		}
	}

	private double getValorTotal(CompraDTO compraDTO, Evento evento) {
		return (compraDTO.getIngressosInteira() * evento.getValorIngresso().doubleValue())
				+ (compraDTO.getIngressosMeia() * (evento.getValorIngresso().doubleValue() / 2));
	}

	private Ingresso criaIngresso(Compra compra, Evento evento, FaixaEtariaEnum faixaEtariaEnum) {
		Ingresso ingresso = new Ingresso();
		ingresso.setCompra(compra);
		ingresso.setFaixaEtaria(faixaEtariaEnum);
		ingresso.setEvento(evento);
		return ingresso;
	}

	public Page<Compra> buscarCompras(Pageable pageable) {
		Usuario usuario = authenticationHelper.getUsuario();
		return compraRepository.findByUsuario(usuario, pageable);
	}


}
