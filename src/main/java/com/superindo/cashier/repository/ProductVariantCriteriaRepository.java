package com.superindo.cashier.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.request.PaginateProductVariantRequest;
import com.superindo.cashier.service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductVariantCriteriaRepository {
	private final EntityManager em;
	private final ProductService productService;

	public Page<ProductVariant> paginate(PaginateProductVariantRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProductVariant> cq = cb.createQuery(ProductVariant.class);

		Root<ProductVariant> root = cq.from(ProductVariant.class);

		Predicate predicate = getPredicate(request, root, cb);
		cq.where(predicate);

		setOrder(request, cb, cq, root);

		List<ProductVariant> symtoms = em.createQuery(cq)
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize).getResultList();

		Pageable pageable = getPageable(request);

		long symtomsCount = getPaginateCount(request);

		return new PageImpl<>(symtoms, pageable, symtomsCount);
	}

	private long getPaginateCount(PaginateProductVariantRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ProductVariant> root = cq.from(ProductVariant.class);

		Predicate predicate = getPredicate(request, root, cb);

		cq.select(cb.count(root));
		cq.where(predicate);

		return em.createQuery(cq).getSingleResult();
	}

	private Pageable getPageable(PaginateProductVariantRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
		return PageRequest.of(pageNumber, pageSize, sort);
	}

	private void setOrder(PaginateProductVariantRequest request, CriteriaBuilder cb, CriteriaQuery<ProductVariant> cq,
			Root<ProductVariant> root) {

		if (request.getSortDirection().equals(Sort.Direction.ASC)) {
			cq.orderBy(cb.asc(root.get(request.getSortBy())));
		}

		else {
			cq.orderBy(cb.desc(root.get(request.getSortBy())));
		}
	}

	private Predicate getPredicate(PaginateProductVariantRequest request, Root<ProductVariant> root,
			CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();

		if (request.getQuery() != null) {
			Predicate nameLike = cb.like(root.get("name"), "%" + request.getQuery() + "%");
			predicates.add(nameLike);

			Predicate codeLike = cb.like(root.get("code"), "%" + request.getQuery() + "%");
			predicates.add(codeLike);
		}

		Predicate productId = cb.equal(root.get("product"), productService.findById(request.getProductId()).get());

		return cb.and(productId, cb.or(predicates.toArray(new Predicate[0])));
	}
}
