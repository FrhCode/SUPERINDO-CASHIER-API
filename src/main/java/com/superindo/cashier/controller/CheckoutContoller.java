package com.superindo.cashier.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.model.Transaction;
import com.superindo.cashier.model.User;
import com.superindo.cashier.repository.TransactionRepository;
import com.superindo.cashier.response.MessageResponse;
import com.superindo.cashier.service.JwtService;
import com.superindo.cashier.service.PdfService;
import com.superindo.cashier.service.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutContoller {

	private final JwtService jwtService;
	private final TransactionService transactionService;
	private final TransactionRepository transactionRepository;
	private final PdfService pdfService;

	@GetMapping("{id}/download")
	public ResponseEntity<Object> download(@PathVariable Long id) throws FileNotFoundException, MalformedURLException {
		Transaction transaction = transactionService.findById(id).get();
		String generateConsultationReport = pdfService.generateInvoice(transaction);

		File file = new File(generateConsultationReport);
		Resource resource = new FileSystemResource(file);

		ContentDisposition contentDisposition = ContentDisposition.builder("inline")
				.filename(transaction.getTransactionNumber() + ".pdf")
				.build();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(contentDisposition);
		headers.setContentType(MediaType.APPLICATION_PDF);

		return ResponseEntity.ok()
				.headers(headers)
				.body(resource);
	}

	@GetMapping
	public ResponseEntity<MessageResponse> index(HttpServletRequest request)
			throws FileNotFoundException, MalformedURLException {
		User user = jwtService.getUser(request);

		Long id = transactionService.save(user).getId();

		return ResponseEntity.ok()
				.body(new MessageResponse(Long.toString(id)));
	}
}
