package com.superindo.cashier.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
@Entity
@Table
public class ProductVariant extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String thumbnail;

	@Column(nullable = false)
	private Long qty;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Boolean active;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnore
	private Product product;

	@JsonProperty("product_id")
	public Long getProductId() {
		return product.getId();
	}
}
