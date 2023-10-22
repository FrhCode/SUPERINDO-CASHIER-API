package com.superindo.cashier.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.superindo.cashier.model.Transaction;
import com.superindo.cashier.request.PaginateTransactionRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TransactionCriteriaRepository {
	private final EntityManager em;

	public Page<Transaction> paginate(PaginateTransactionRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);

		Root<Transaction> root = cq.from(Transaction.class);

		Predicate predicate = getPredicate(request, root, cb);
		cq.where(predicate);

		setOrder(request, cb, cq, root);

		List<Transaction> symtoms = em.createQuery(cq)
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize).getResultList();

		Pageable pageable = getPageable(request);

		long symtomsCount = getPaginateCount(request);

		return new PageImpl<>(symtoms, pageable, symtomsCount);
	}

	private long getPaginateCount(PaginateTransactionRequest request) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Transaction> root = cq.from(Transaction.class);

		Predicate predicate = getPredicate(request, root, cb);

		cq.select(cb.count(root));
		cq.where(predicate);

		return em.createQuery(cq).getSingleResult();
	}

	private Pageable getPageable(PaginateTransactionRequest request) {
		Integer pageNumber = request.getPage();
		Integer pageSize = request.getSize();

		Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
		return PageRequest.of(pageNumber, pageSize, sort);
	}

	private void setOrder(PaginateTransactionRequest request, CriteriaBuilder cb, CriteriaQuery<Transaction> cq,
			Root<Transaction> root) {

		if (request.getSortDirection().equals(Sort.Direction.ASC)) {
			cq.orderBy(cb.asc(root.get(request.getSortBy())));
		}

		else {
			cq.orderBy(cb.desc(root.get(request.getSortBy())));
		}
	}

	private Predicate getPredicate(PaginateTransactionRequest request, Root<Transaction> root,
			CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<>();

		// if (request.getQuery() != null) {
		// Predicate nameLike = cb.like(
		// cb.lower(root.get("name")),
		// "%" + request.getQuery().toLowerCase() + "%");

		// predicates.add(nameLike);
		// }
		predicates.add(cb.equal(cb.literal(1), cb.literal(1)));

		return cb.or(predicates.toArray(new Predicate[0]));
	}
}
