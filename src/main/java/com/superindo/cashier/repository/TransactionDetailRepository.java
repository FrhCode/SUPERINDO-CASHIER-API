package com.superindo.cashier.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superindo.cashier.model.TransactionDetail;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Long> {

}
