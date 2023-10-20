package com.superindo.cashier.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.request.PaginateProductVariantRequest;
import com.superindo.cashier.service.ProductVariantService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/product_variants")
@RequiredArgsConstructor
public class ProductVariantController {
	private final ProductVariantService productVariantService;

	@GetMapping
	public Page<ProductVariant> index(PaginateProductVariantRequest paginateProductVariantRequest) {
		return productVariantService.paginate(paginateProductVariantRequest);
	}

}
