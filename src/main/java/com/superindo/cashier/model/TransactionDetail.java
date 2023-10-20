package com.superindo.cashier.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
@Entity
@Table
public class TransactionDetail extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Long qty;

	@Column(nullable = false, name = "subtotal")
	private BigDecimal subTotal;

	@Column(nullable = false)
	private Boolean active;

	@ManyToOne
	@JsonIgnore
	private Transaction transaction;

	@OneToOne
	@JsonIgnore
	private ProductVariant productVariant;
}
