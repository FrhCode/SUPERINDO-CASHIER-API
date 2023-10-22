package com.superindo.cashier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.model.Transaction;
import com.superindo.cashier.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RestController
@EnableJpaAuditing
@RequiredArgsConstructor
public class CashierApplication {
	private final TransactionRepository transactionRepository;

	@GetMapping
	public Transaction index() {
		Transaction transaction = transactionRepository.findById(1L).get();
		return transaction;
	}

	public static void main(String[] args) {
		SpringApplication.run(CashierApplication.class, args);
	}

}
