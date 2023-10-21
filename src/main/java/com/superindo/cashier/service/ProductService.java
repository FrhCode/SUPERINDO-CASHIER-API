package com.superindo.cashier.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.superindo.cashier.model.Product;
import com.superindo.cashier.repository.ProductCriteriaRepository;
import com.superindo.cashier.request.PaginateProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductCriteriaRepository productCriteriaRepository;

	public Page<Product> paginate(PaginateProductRequest request) {
		return productCriteriaRepository.paginate(request);
	}
}
