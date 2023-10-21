package com.superindo.cashier.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.model.Product;
import com.superindo.cashier.request.PaginateProductRequest;
import com.superindo.cashier.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@GetMapping
	public Page<Product> index(PaginateProductRequest request) {
		return productService.paginate(request);
	}
}
