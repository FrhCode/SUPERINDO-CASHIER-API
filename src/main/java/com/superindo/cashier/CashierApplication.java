package com.superindo.cashier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.repository.ProductVariantCriteriaRepository;
import com.superindo.cashier.request.PaginateProductVariantRequest;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RestController
@EnableJpaAuditing
@RequiredArgsConstructor
public class CashierApplication {
	private final ProductVariantCriteriaRepository productVariantCriteriaRepository;

	@GetMapping
	public Page<ProductVariant> index(PaginateProductVariantRequest paginateProductVariantRequest) {
		return productVariantCriteriaRepository.paginate(paginateProductVariantRequest);
	}

	public static void main(String[] args) {
		SpringApplication.run(CashierApplication.class, args);
	}

}
