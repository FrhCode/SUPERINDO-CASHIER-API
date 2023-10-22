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
import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.request.PaginateProductVariantRequest;
import com.superindo.cashier.request.UpdateProductVariantRequest;
import com.superindo.cashier.response.MessageResponse;
import com.superindo.cashier.service.ProductVariantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product_variants")
@RequiredArgsConstructor
public class ProductVariantController {
	private final ProductVariantService productVariantService;

	@GetMapping
	public Page<ProductVariant> index(PaginateProductVariantRequest request) {
		return productVariantService.paginate(request);
	}

	@PutMapping("{id}")
	public ResponseEntity<MessageResponse<String>> update(@PathVariable Long id,
			@Valid @RequestBody UpdateProductVariantRequest request) {
		Optional<ProductVariant> optionalProductVariant = productVariantService.findById(id);

		if (optionalProductVariant.isEmpty()) {
			throw new ResourceNotFoundException("Product Variant Not Found");
		}

		productVariantService.update(optionalProductVariant.get(), request);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<String>("Created"));
	}

	@PostMapping
	public ResponseEntity<MessageResponse<String>> save(
			@Valid @RequestBody UpdateProductVariantRequest request) {

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse<String>("Created"));
	}

}
