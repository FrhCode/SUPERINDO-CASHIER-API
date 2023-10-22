package com.superindo.cashier.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.model.Transaction;
import com.superindo.cashier.response.BaseResponse;
import com.superindo.cashier.response.MessageResponse;
import com.superindo.cashier.service.ProductService;
import com.superindo.cashier.service.ProductVariantService;
import com.superindo.cashier.service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/overview")
@RequiredArgsConstructor
public class OverviewController {
	private final TransactionService transactionService;
	private final ProductService productService;
	private final ProductVariantService productVariantService;

	@GetMapping("total-transaction-ammount")
	public ResponseEntity<Object> totalTransactionAmmount(HttpServletRequest request) {

		BigDecimal ammount = transactionService.totalTransactionAmmount();

		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<BigDecimal>(ammount));
	}

	@GetMapping("total-transaction-count")
	public ResponseEntity<Object> totalTransactionCount(HttpServletRequest request) {

		long count = transactionService.count();

		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<Long>(count));
	}

	@GetMapping("total-product")
	public ResponseEntity<Object> totalProduct(HttpServletRequest request) {

		long count = productService.count();

		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<Long>(count));
	}

	@GetMapping("total-variant")
	public ResponseEntity<Object> totalVariant(HttpServletRequest request) {

		long count = productVariantService.count();

		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<Long>(count));
	}

	@GetMapping("latest-transaction")
	public ResponseEntity<Object> latestTransaction(HttpServletRequest request) {

		List<Transaction> transactions = transactionService.latest(10);

		return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<Transaction>(transactions));
	}
}
