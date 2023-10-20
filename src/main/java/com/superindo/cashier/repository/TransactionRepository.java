package com.superindo.cashier.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superindo.cashier.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	long countByCreatedDateIsAfter(Date startOfToday);

}
