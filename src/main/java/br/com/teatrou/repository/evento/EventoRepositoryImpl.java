package br.com.teatrou.repository.evento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.teatrou.model.Evento;
import br.com.teatrou.model.Evento_;
import br.com.teatrou.repository.filter.EventoFilter;

public class EventoRepositoryImpl implements EventoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Evento> filtrar(EventoFilter eventoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Evento> criteria = builder.createQuery(Evento.class);
		Root<Evento> root = criteria.from(Evento.class);

		Predicate[] predicates = filtrarPesquisa(eventoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Evento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, totalElements(eventoFilter));
	}

	private Predicate[] filtrarPesquisa(EventoFilter eventoFilter, CriteriaBuilder builder, Root<Evento> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (!StringUtils.isEmpty(eventoFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get(Evento_.descricao)),
					"%" + eventoFilter.getDescricao().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(eventoFilter.getTema())) {
			predicates.add(builder.equal(root.get(Evento_.tema), eventoFilter.getTema()));
		}
		if (!StringUtils.isEmpty(eventoFilter.getTitulo())) {
			predicates.add(builder.like(builder.lower(root.get(Evento_.titulo)),
					"%" + eventoFilter.getTitulo().toLowerCase() + "%"));
		}
		if (!StringUtils.isEmpty(eventoFilter.getDataEventoDe())) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Evento_.dataEvento), eventoFilter.getDataEventoDe()));
		}
		if (!StringUtils.isEmpty(eventoFilter.getDataEventoAte())) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Evento_.dataEvento), eventoFilter.getDataEventoAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Evento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalDeRegistros = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalDeRegistros;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalDeRegistros);
	}

	private Long totalElements(EventoFilter eventoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Evento> root = criteria.from(Evento.class);

		Predicate[] predicates = filtrarPesquisa(eventoFilter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));

		return manager.createQuery(criteria).getSingleResult();
	}

}
