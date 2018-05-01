package br.com.teatrou.repository.compra;

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

import br.com.teatrou.model.Compra;
import br.com.teatrou.model.Compra_;
import br.com.teatrou.repository.filter.CompraFilter;
import br.com.teatrou.token.AuthenticationHelper;

public class CompraRepositoryImpl implements CompraRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private AuthenticationHelper authenticationHelper;

	@Override
	public Page<Compra> filtrar(CompraFilter compraFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Compra> criteria = builder.createQuery(Compra.class);
		Root<Compra> root = criteria.from(Compra.class);

		Predicate[] predicates = filtrarPesquisa(compraFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Compra> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, totalElements(compraFilter));
	}

	private Predicate[] filtrarPesquisa(CompraFilter compraFilter, CriteriaBuilder builder, Root<Compra> root) {
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (authenticationHelper.getUser() != null) {
			predicates.add(builder.equal(root.get(Compra_.usuario), authenticationHelper.getUsuario()));
		}
		if (!StringUtils.isEmpty(compraFilter.getDataEventoDe())) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Compra_.dataCompra), compraFilter.getDataEventoDe()));
		}
		if (!StringUtils.isEmpty(compraFilter.getDataEventoAte())) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Compra_.dataCompra), compraFilter.getDataEventoAte()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Compra> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalDeRegistros = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalDeRegistros;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalDeRegistros);
	}

	private Long totalElements(CompraFilter compraFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Compra> root = criteria.from(Compra.class);

		Predicate[] predicates = filtrarPesquisa(compraFilter, builder, root);
		criteria.where(predicates);
		criteria.select(builder.count(root));

		return manager.createQuery(criteria).getSingleResult();
	}

}
