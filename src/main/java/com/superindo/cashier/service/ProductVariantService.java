package com.superindo.cashier.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.superindo.cashier.model.Product;
import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.repository.ProductVariantCriteriaRepository;
import com.superindo.cashier.repository.ProductVariantRepository;
import com.superindo.cashier.request.CreateProductVariantRequest;
import com.superindo.cashier.request.PaginateProductVariantRequest;
import com.superindo.cashier.request.UpdateProductVariantRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductVariantService {
	private final ProductVariantCriteriaRepository productVariantCriteriaRepository;
	private final ProductVariantRepository productVariantRepository;

	public Optional<ProductVariant> findById(Long id) {
		return productVariantRepository.findById(id);
	}

	public Page<ProductVariant> paginate(PaginateProductVariantRequest paginateProductVariantRequest) {
		return productVariantCriteriaRepository.paginate(paginateProductVariantRequest);
	}

	public void save(ProductVariant productVariant) {
		productVariantRepository.save(productVariant);
	}

	public void save(Product product, CreateProductVariantRequest request) {
		ProductVariant productVariant = new ProductVariant();
		productVariant.setActive(request.getActive());
		productVariant.setCode(request.getCode());
		productVariant.setName(request.getName());
		productVariant.setPrice(request.getPrice());
		productVariant.setProduct(product);
		productVariant.setQty(request.getQty());
		productVariant.setThumbnail(request.getThumbnail());

		save(productVariant);
	}

	public void update(ProductVariant productVariant, UpdateProductVariantRequest request) {
		productVariant.setActive(request.getActive());
		productVariant.setName(request.getName());
		productVariant.setPrice(request.getPrice());
		productVariant.setQty(request.getQty());
		productVariant.setThumbnail(request.getThumbnail());

		save(productVariant);
	}
}
