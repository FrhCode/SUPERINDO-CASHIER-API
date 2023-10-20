package com.superindo.cashier.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.superindo.cashier.model.ProductCategory;
import com.superindo.cashier.repository.ProductCategoryCriteriaRepository;
import com.superindo.cashier.request.PaginateProductCategoryRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
	private final ProductCategoryCriteriaRepository productCategoryCriteriaRepository;

	public Page<ProductCategory> paginate(PaginateProductCategoryRequest paginateProductCategoryRequest) {
		return productCategoryCriteriaRepository.paginate(paginateProductCategoryRequest);
	}
}
