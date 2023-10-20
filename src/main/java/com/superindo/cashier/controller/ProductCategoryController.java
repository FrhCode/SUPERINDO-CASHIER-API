package com.superindo.cashier.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.model.ProductCategory;
import com.superindo.cashier.request.PaginateProductCategoryRequest;
import com.superindo.cashier.service.ProductCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product_category")
@RequiredArgsConstructor
public class ProductCategoryController {
	private final ProductCategoryService productCategoryService;

	@GetMapping
	public Page<ProductCategory> index(PaginateProductCategoryRequest paginateProductCategoryRequest) {
		return productCategoryService.paginate(paginateProductCategoryRequest);
	}
}
