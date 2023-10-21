package com.superindo.cashier;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.exception.ResourceNotFoundException;
import com.superindo.cashier.model.Product;
import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.repository.ProductRepository;
import com.superindo.cashier.repository.ProductVariantCriteriaRepository;
import com.superindo.cashier.request.PaginateProductVariantRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RestController
@EnableJpaAuditing
@RequiredArgsConstructor
public class CashierApplication {
	private final ProductVariantCriteriaRepository productVariantCriteriaRepository;
	private final ProductRepository productRepository;

	@GetMapping
	public Page<ProductVariant> index(@Valid PaginateProductVariantRequest request) {
		Optional<Product> optionalProduct = productRepository.findById(request.getProductId());

		if (optionalProduct.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		return productVariantCriteriaRepository.paginate(request);
	}

	public static void main(String[] args) {
		SpringApplication.run(CashierApplication.class, args);
	}

}
