package com.superindo.cashier.service;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.superindo.cashier.model.Transaction;

@SpringBootTest
public class PdfServiceTest {
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private PdfService pdfService;

	@Test
	@Transactional
	void testGenerateConsultationReport() throws FileNotFoundException, MalformedURLException {

		Transaction transaction = transactionService.findById(1L).get();

		pdfService.generateInvoice(transaction);

	}
}
