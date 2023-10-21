package com.superindo.cashier.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.superindo.cashier.model.Product;
import com.superindo.cashier.request.PaginateProductRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductCriteriaRepository {
	private final EntityManager em;

	public Page<Product> paginate(PaginateProductRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> cq = cb.createQuery(Product.class);

		Root<Product> root = cq.from(Product.class);

		Predicate predicate = getPredicate(request, root, cb);
		cq.where(predicate);

		setOrder(request, cb, cq, root);

		List<Product> symtoms = em.createQuery(cq)
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize).getResultList();

		Pageable pageable = getPageable(request);

		long symtomsCount = getPaginateCount(request);

		return new PageImpl<>(symtoms, pageable, symtomsCount);
	}

	private long getPaginateCount(PaginateProductRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Product> root = cq.from(Product.class);

		Predicate predicate = getPredicate(request, root, cb);

		cq.select(cb.count(root));
		cq.where(predicate);

		return em.createQuery(cq).getSingleResult();
	}

	private Pageable getPageable(PaginateProductRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
		return PageRequest.of(pageNumber, pageSize, sort);
	}

	private void setOrder(PaginateProductRequest request, CriteriaBuilder cb, CriteriaQuery<Product> cq,
			Root<Product> root) {

		if (request.getSortDirection().equals(Sort.Direction.ASC)) {
			cq.orderBy(cb.asc(root.get(request.getSortBy())));
		}

		else {
			cq.orderBy(cb.desc(root.get(request.getSortBy())));
		}
	}

	private Predicate getPredicate(PaginateProductRequest request, Root<Product> root,
			CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();

		if (request.getQuery() != null) {
			Predicate nameLike = cb.like(
					cb.lower(root.get("name")),
					"%" + request.getQuery().toLowerCase() + "%");

			predicates.add(nameLike);
		}

		return cb.or(predicates.toArray(new Predicate[0]));
	}
}
