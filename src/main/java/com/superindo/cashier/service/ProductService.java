package com.superindo.cashier.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.superindo.cashier.model.Product;
import com.superindo.cashier.model.ProductCategory;
import com.superindo.cashier.repository.ProductCriteriaRepository;
import com.superindo.cashier.repository.ProductRepository;
import com.superindo.cashier.request.CreateProductRequest;
import com.superindo.cashier.request.PaginateProductRequest;
import com.superindo.cashier.request.UpdateProductRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductCriteriaRepository productCriteriaRepository;
	private final ProductRepository productRepository;

	public Page<Product> paginate(PaginateProductRequest request) {
		return productCriteriaRepository.paginate(request);
	}

	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	public void save(Product product) {
		productRepository.save(product);
	}

	public void save(ProductCategory productCategory, CreateProductRequest request) {
		Product product = new Product();
		product.setActive(request.getActive());
		product.setName(request.getName());
		product.setPlu(request.getPlu());
		product.setProductCategory(productCategory);

		save(product);
	}

	public void save(Product product, UpdateProductRequest request) {
		product.setActive(request.getActive());
		product.setName(request.getName());

		save(product);
	}
}
