package br.com.teatrou.service;


import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.teatrou.model.Evento;
import br.com.teatrou.repository.EventoRepository;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;

	public Page<Evento> listar(Pageable pageable) {
	   return eventoRepository.findAll(pageable);
	}

	public Evento salvar(Evento evento) {
		return eventoRepository.save(evento);
	}

	public Evento atualizar(Long codigo, Evento evento) {
		Evento eventoSalvo = BuscaPeloCodigo(codigo);
		BeanUtils.copyProperties(evento, eventoSalvo, "codigo");
		return salvar(eventoSalvo);
	}

	private Evento BuscaPeloCodigo(Long codigo) {
		Evento eventoSalvo = eventoRepository.findOne(codigo);
		if (eventoSalvo == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return eventoSalvo;
	}

	public Evento atualizarDataEvento(Long codigo, LocalDate dataEvento) {
		Evento eventoSalvo = BuscaPeloCodigo(codigo);
		eventoSalvo.setDataEvento(dataEvento);
		return salvar(eventoSalvo);
	}

	public Evento ativarEvento(Long codigo) {
		Evento eventoSalvo = BuscaPeloCodigo(codigo);
		eventoSalvo.setAtivo(true);
		return salvar(eventoSalvo);
	}

	public Evento atualizarDescricao(Long codigo, String descricao) {
		Evento eventoSalvo = BuscaPeloCodigo(codigo);
		eventoSalvo.setDescricao(descricao);
		return salvar(eventoSalvo);
	}
	

}
