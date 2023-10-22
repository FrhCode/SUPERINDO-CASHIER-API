package com.superindo.cashier.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.model.Transaction;
import com.superindo.cashier.request.PaginateTransactionRequest;
import com.superindo.cashier.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
	private final TransactionService transactionService;

	@GetMapping
	public Page<Transaction> index(PaginateTransactionRequest request) {
		return transactionService.paginate(request);

	}
}
