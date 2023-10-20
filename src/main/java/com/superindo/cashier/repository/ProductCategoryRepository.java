package com.superindo.cashier.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superindo.cashier.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	Optional<ProductCategory> findByName(String name);

}
