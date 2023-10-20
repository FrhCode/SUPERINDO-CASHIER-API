package com.superindo.cashier.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superindo.cashier.model.Transaction;
import com.superindo.cashier.model.TransactionDetail;
import com.superindo.cashier.repository.TransactionDetailRepository;
import com.superindo.cashier.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final TransactionDetailRepository transactionDetailRepository;

	private long countInvoiceForADate(LocalDateTime dateTime) {
		Calendar calendar = Calendar.getInstance(); // Get a Calendar instance
		calendar.setTime(new Date()); // Set the calendar to the current date and time

		// Set the time fields (hours, minutes, seconds, milliseconds) to zero
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date startOfToday = calendar.getTime();
		return transactionRepository.countByCreatedDateIsAfter(startOfToday);
	}

	private String generateInvoiceNumber(LocalDateTime dateTime) {
		// Get the current date in the YMD format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		String currentDate = sdf.format(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));

		// Get the current sequence for the day
		long sequence = countInvoiceForADate(dateTime) + 1;

		// Generate the invoice number
		String invoiceNumber = "INV/" + currentDate + "/" + String.format("%06d", sequence);

		return invoiceNumber;
	}

	private String generateInvoiceNumber() {
		return generateInvoiceNumber(LocalDateTime.now());
	}

	@Transactional
	public void save(List<TransactionDetail> transactionDetails) {
		Transaction transaction = new Transaction();
		transaction.setActive(true);
		transaction.setTransactionNumber(generateInvoiceNumber());
		transaction.setTotalAmount(new BigDecimal("0"));
		transactionRepository.save(transaction);

		BigDecimal totalAmount = new BigDecimal(0);

		for (TransactionDetail transactionDetail : transactionDetails) {
			Long qty = transactionDetail.getQty();
			BigDecimal price = transactionDetail.getPrice();

			BigDecimal subTotal = price.multiply(BigDecimal.valueOf(qty));

			totalAmount.add(subTotal);

			transactionDetail.setActive(true);
			transactionDetail.setSubTotal(subTotal);
			transactionDetail.setTransaction(transaction);
			transactionDetailRepository.save(transactionDetail);
		}

		transaction.setTotalAmount(totalAmount);
	}
}
