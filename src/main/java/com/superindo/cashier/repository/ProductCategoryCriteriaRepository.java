package com.superindo.cashier.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.superindo.cashier.model.ProductCategory;
import com.superindo.cashier.request.PaginateProductCategoryRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductCategoryCriteriaRepository {
	private final EntityManager em;

	public Page<ProductCategory> paginate(PaginateProductCategoryRequest request) {
		Integer pageNumber = request.getPage() - 1;
		Integer pageSize = request.getSize();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProductCategory> cq = cb.createQuery(ProductCategory.class);

		Root<ProductCategory> root = cq.from(ProductCategory.class);

		Predicate predicate = getPredicate(request, root, cb);
		cq.where(predicate);

		setOrder(request, cb, cq, root);

		List<ProductCategory> symtoms = em.createQuery(cq)
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize).getResultList();

		Pageable pageable = getPageable(request);

		long symtomsCount = getPaginateCount(request);

		return new PageImpl<>(symtoms, pageable, symtomsCount);
	}

	private long getPaginateCount(PaginateProductCategoryRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ProductCategory> root = cq.from(ProductCategory.class);

		Predicate predicate = getPredicate(request, root, cb);

		cq.select(cb.count(root));
		cq.where(predicate);

		return em.createQuery(cq).getSingleResult();
	}

	private Pageable getPageable(PaginateProductCategoryRequest request) {
		Integer pageNumber = request.getPage() - 1;
		Integer pageSize = request.getSize();

		Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
		return PageRequest.of(pageNumber, pageSize, sort);
	}

	private void setOrder(PaginateProductCategoryRequest request, CriteriaBuilder cb, CriteriaQuery<ProductCategory> cq,
			Root<ProductCategory> root) {

		if (request.getSortDirection().equals(Sort.Direction.ASC)) {
			cq.orderBy(cb.asc(root.get(request.getSortBy())));
		}

		else {
			cq.orderBy(cb.desc(root.get(request.getSortBy())));
		}
	}

	private Predicate getPredicate(PaginateProductCategoryRequest request, Root<ProductCategory> root,
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
