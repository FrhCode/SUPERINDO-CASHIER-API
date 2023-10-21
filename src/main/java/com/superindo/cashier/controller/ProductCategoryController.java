package com.superindo.cashier.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.exception.ResourceNotFoundException;
import com.superindo.cashier.model.ProductCategory;
import com.superindo.cashier.request.CreateProductCategoryRequest;
import com.superindo.cashier.request.PaginateProductCategoryRequest;
import com.superindo.cashier.request.UpdateProductCategoryRequest;
import com.superindo.cashier.response.MessageResponse;
import com.superindo.cashier.service.ProductCategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product_categories")
@RequiredArgsConstructor
public class ProductCategoryController {
	private final ProductCategoryService productCategoryService;

	@GetMapping
	public Page<ProductCategory> index(PaginateProductCategoryRequest request) {
		return productCategoryService.paginate(request);
	}

	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody CreateProductCategoryRequest request) {
		productCategoryService.create(request);

		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("success"));
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> update(@Valid @RequestBody UpdateProductCategoryRequest request,
			@PathVariable Long id) {
		Optional<ProductCategory> optionalProductCategory = productCategoryService.findById(id);

		if (optionalProductCategory.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		ProductCategory productCategory = optionalProductCategory.get();
		productCategory.setActive(request.getActive());
		productCategory.setName(request.getName());
		productCategoryService.update(productCategory);

		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("success"));
	}
}
