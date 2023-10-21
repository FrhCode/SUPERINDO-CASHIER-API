package com.superindo.cashier.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
@Entity
@Table
public class Product extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String plu;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Boolean active;

	@Column(nullable = false)
	private String thumbnail;

	@ManyToOne
	@JoinColumn(name = "product_category_id")
	@JsonIgnore
	private ProductCategory productCategory;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private Set<ProductVariant> productVariants;

	@JsonProperty("product_category_id")
	public Long getProductCategoryId() {
		return productCategory.getId();
	}
}
