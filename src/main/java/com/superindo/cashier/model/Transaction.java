package com.superindo.cashier.model;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
@Entity
@Table(name = "transaction_table")
public class Transaction extends Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "transaction_no")
	private String transactionNumber;

	@Column(nullable = false)
	private BigDecimal totalAmount;

	@Column(nullable = false)
	private Boolean active;

	@OneToMany(mappedBy = "transaction")
	@JsonIgnore
	private Set<TransactionDetail> transactionDetails;
}
