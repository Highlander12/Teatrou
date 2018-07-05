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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.teatrou.model.Compra_;
import br.com.teatrou.model.Evento;
import br.com.teatrou.model.Evento_;
import br.com.teatrou.model.Usuario_;
import br.com.teatrou.repository.filter.EventoFilter;
import br.com.teatrou.repository.projection.ResumoEvento;
import br.com.teatrou.token.AuthenticationHelper;

public class EventoRepositoryImpl implements EventoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private AuthenticationHelper authenticationHelper;

	@Override
	public Page<ResumoEvento> filtrar(EventoFilter eventoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoEvento> criteria = builder.createQuery(ResumoEvento.class);
		Root<Evento> root = criteria.from(Evento.class);
		resumir(builder, criteria, root);
		Predicate[] predicates = filtrarPesquisa(eventoFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<ResumoEvento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, totalElements(eventoFilter));
	}

	private void resumir(CriteriaBuilder builder, CriteriaQuery<ResumoEvento> criteria, Root<Evento> root) {
		criteria.select(builder.construct(ResumoEvento.class, root.get(Evento_.codigo),
				root.get(Evento_.usuario).get(Usuario_.nome), root.get(Evento_.anexo), root.get(Evento_.titulo),
				root.get(Evento_.descricao), root.get(Evento_.dataEvento), root.get(Evento_.horaInicial),
				root.get(Evento_.horaFinal), root.get(Evento_.tema), root.get(Evento_.endereco),
				root.get(Evento_.quantidadeIngresso), root.get(Evento_.valorIngresso), root.get(Evento_.ativo)));
	}

	private Predicate[] filtrarPesquisa(EventoFilter eventoFilter, CriteriaBuilder builder, Root<Evento> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (!StringUtils.isEmpty(eventoFilter.getCodigoUsuario())) {
			predicates.add(
					builder.equal(root.get(Evento_.usuario).get(Usuario_.codigo), eventoFilter.getCodigoUsuario()));
		}
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
		if (!StringUtils.isEmpty(eventoFilter.getAtivo())) {
			predicates.add(builder.isTrue(builder.equal(root.get(Evento_.ativo), eventoFilter.getAtivo())));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
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
