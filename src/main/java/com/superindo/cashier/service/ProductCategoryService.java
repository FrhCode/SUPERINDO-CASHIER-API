package com.superindo.cashier.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.superindo.cashier.model.ProductCategory;
import com.superindo.cashier.repository.ProductCategoryCriteriaRepository;
import com.superindo.cashier.repository.ProductCategoryRepository;
import com.superindo.cashier.request.CreateProductCategoryRequest;
import com.superindo.cashier.request.PaginateProductCategoryRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
	private final ProductCategoryCriteriaRepository productCategoryCriteriaRepository;
	private final ProductCategoryRepository productCategoryRepository;

	public Page<ProductCategory> paginate(PaginateProductCategoryRequest paginateProductCategoryRequest) {
		return productCategoryCriteriaRepository.paginate(paginateProductCategoryRequest);
	}

	public void update(ProductCategory productCategory) {
		productCategoryRepository.save(productCategory);
	}

	public Optional<ProductCategory> findById(Long id) {
		return productCategoryRepository.findById(id);
	}

	public void create(@Valid CreateProductCategoryRequest request) {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setActive(request.getActive());
		productCategory.setName(request.getName());

		productCategoryRepository.save(productCategory);
	}
}
