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
import com.superindo.cashier.model.Product;
import com.superindo.cashier.model.ProductCategory;
import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.request.CreateProductRequest;
import com.superindo.cashier.request.CreateProductVariantRequest;
import com.superindo.cashier.request.PaginateProductRequest;
import com.superindo.cashier.request.PaginateProductVariantRequest;
import com.superindo.cashier.request.UpdateProductRequest;
import com.superindo.cashier.response.MessageResponse;
import com.superindo.cashier.service.ProductCategoryService;
import com.superindo.cashier.service.ProductService;
import com.superindo.cashier.service.ProductVariantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;
	private final ProductVariantService productVariantService;
	private final ProductCategoryService productCategoryService;

	@GetMapping("{productId}/variants")
	public Page<ProductVariant> paginateVariant(@PathVariable Long productId,
			PaginateProductVariantRequest request) {
		Optional<Product> optionalProduct = productService.findById(request.getProductId());
		request.setProductId(productId);
		if (optionalProduct.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		return productVariantService.paginate(request);
	}

	@GetMapping
	public Page<Product> index(PaginateProductRequest request) {
		return productService.paginate(request);
	}

	@PostMapping
	public ResponseEntity<MessageResponse> save(@Valid @RequestBody CreateProductRequest request) {
		Optional<ProductCategory> optionalProductCategory = productCategoryService.findById(request.getProductCategoryId());

		if (optionalProductCategory.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		productService.save(optionalProductCategory.get(), request);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Created"));
	}

	@PutMapping("{id}")
	public ResponseEntity<MessageResponse> update(@PathVariable Long id,
			@Valid @RequestBody UpdateProductRequest request) {

		Optional<Product> optionalProduct = productService.findById(id);

		if (optionalProduct.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		productService.save(optionalProduct.get(), request);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Created"));
	}

	// @GetMapping("{id}/variants")
	// public BaseResponse<ProductVariant> variant(@PathVariable Long id) {

	// Optional<Product> optionalProduct = productService.findById(id);

	// if (optionalProduct.isEmpty()) {
	// throw new ResourceNotFoundException();
	// }

	// Product product = optionalProduct.get();

	// BaseResponse<ProductVariant> response = new BaseResponse<>();
	// response.setContent(new ArrayList<>(product.getProductVariants()));
	// return response;
	// }

	@PostMapping("{id}/variants")
	public ResponseEntity<MessageResponse> createVariant(@PathVariable Long id,
			@Valid @RequestBody CreateProductVariantRequest request) {
		Optional<Product> optionalProduct = productService.findById(id);

		if (optionalProduct.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		productVariantService.save(optionalProduct.get(), request);

		return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Created"));
	}
}
