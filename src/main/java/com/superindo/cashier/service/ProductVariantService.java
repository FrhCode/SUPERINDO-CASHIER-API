package com.superindo.cashier.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.repository.ProductVariantCriteriaRepository;
import com.superindo.cashier.request.PaginateProductVariantRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductVariantService {
	private final ProductVariantCriteriaRepository productVariantCriteriaRepository;

	public Page<ProductVariant> paginate(PaginateProductVariantRequest paginateProductVariantRequest) {
		return productVariantCriteriaRepository.paginate(paginateProductVariantRequest);
	}
}
