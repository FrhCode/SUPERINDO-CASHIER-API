package com.superindo.cashier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superindo.cashier.model.ProductVariant;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
	Optional<ProductVariant> findByName(String name);

}
