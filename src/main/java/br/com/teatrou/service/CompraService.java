package br.com.teatrou.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
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

	public Compra comprar(CompraDTO compraDTO) {
		Usuario usuario = authenticationHelper.getUsuario();
		Compra compra = new Compra();
		compra.setQuantidadeIngresso(compraDTO.getIngressosInteira() + compraDTO.getIngressosMeia());
		compra.setUsuario(usuario);
		compra.setDataCompra(LocalDate.now());
		Evento evento = eventoRepository.findByUsuario(usuario);
		Double valorTotal = (compraDTO.getIngressosInteira() * evento.getValorIngresso().doubleValue())
				+ (compraDTO.getIngressosMeia() * (evento.getValorIngresso().doubleValue() / 2));
		compra.setValorTotal(BigDecimal.valueOf(valorTotal));
		Compra compraSave = compraRepository.save(compra);

		Integer loop = compraDTO.getIngressosInteira().intValue();
		Integer quantidadeTotalIngressos = evento.getQuantidadeIngresso();
		for (int i = 0; i < loop; i++) {
          ingressoRepository.save(criaIngresso(compraSave, evento, FaixaEtariaEnum.INTEIRA));
		}
		return null;
	}

	private Ingresso criaIngresso(Compra compra, Evento evento, FaixaEtariaEnum faixaEtariaEnum) {
		Ingresso ingresso = new Ingresso();
		ingresso.setCompra(compra);
		ingresso.setFaixaEtaria(faixaEtariaEnum);
		ingresso.setEvento(evento);
		return ingresso;
	}

}
