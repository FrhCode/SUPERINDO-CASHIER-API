package com.superindo.cashier.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superindo.cashier.model.Cart;
import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.model.Transaction;
import com.superindo.cashier.model.TransactionDetail;
import com.superindo.cashier.model.User;
import com.superindo.cashier.repository.TransactionDetailRepository;
import com.superindo.cashier.repository.TransactionRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
	private final TransactionRepository transactionRepository;
	private final ProductVariantService productVariantService;
	private final TransactionDetailRepository transactionDetailRepository;
	private final CartService cartService;
	private final EntityManager em;

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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String currentDate = sdf.format(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));

		// Get the current sequence for the day
		long sequence = countInvoiceForADate(dateTime) + 1;

		// Generate the invoice number
		String invoiceNumber = "INV-" + currentDate + "-" + String.format("%06d", sequence);

		return invoiceNumber;
	}

	private String generateInvoiceNumber() {
		return generateInvoiceNumber(LocalDateTime.now());
	}

	public Optional<Transaction> findById(Long id) {
		return transactionRepository.findById(id);
	}

	@Transactional
	public Transaction save(List<TransactionDetail> transactionDetails) {
		Transaction transaction = new Transaction();
		transaction.setActive(true);
		transaction.setTransactionNumber(generateInvoiceNumber());
		transaction.setTotalAmount(new BigDecimal("0"));
		transactionRepository.save(transaction);

		BigDecimal totalAmount = new BigDecimal("0");

		for (TransactionDetail transactionDetail : transactionDetails) {
			Long qty = transactionDetail.getQty();
			BigDecimal price = transactionDetail.getPrice();

			BigDecimal subTotal = price.multiply(BigDecimal.valueOf(qty));

			totalAmount = totalAmount.add(subTotal);

			transactionDetail.setActive(true);
			transactionDetail.setSubTotal(subTotal);
			transactionDetail.setTransaction(transaction);
			transactionDetailRepository.save(transactionDetail);
		}

		transaction.setTotalAmount(totalAmount);
		transactionRepository.save(transaction);

		return transaction;
	}

	@Transactional
	public Transaction save(List<TransactionDetail> transactionDetails, User user) {
		Transaction transaction = new Transaction();
		transaction.setActive(true);
		transaction.setTransactionNumber(generateInvoiceNumber());
		transaction.setTotalAmount(new BigDecimal("0"));
		transactionRepository.save(transaction);

		BigDecimal totalAmount = new BigDecimal("0");

		for (TransactionDetail transactionDetail : transactionDetails) {
			Long qty = transactionDetail.getQty();
			BigDecimal price = transactionDetail.getPrice();

			BigDecimal subTotal = price.multiply(BigDecimal.valueOf(qty));

			totalAmount = totalAmount.add(subTotal);

			transactionDetail.setActive(true);
			transactionDetail.setSubTotal(subTotal);
			transactionDetail.setTransaction(transaction);
			transactionDetailRepository.save(transactionDetail);

			ProductVariant productVariant = productVariantService.findById(transactionDetail.getProductVariant().getId())
					.get();
			productVariant.setQty(productVariant.getQty() - transactionDetail.getQty());
			productVariantService.save(productVariant);

		}

		transaction.setTotalAmount(totalAmount);
		transactionRepository.save(transaction);

		List<Cart> carts = cartService.findByUser(user);
		cartService.delete(carts);

		return transaction;
	}

	public Transaction save(User user) {
		List<TransactionDetail> transactionDetails = new ArrayList<>();

		List<Cart> carts = cartService.findByUser(user);

		for (Cart cart : carts) {
			TransactionDetail transactionDetail = new TransactionDetail();
			transactionDetail.setProductVariant(cart.getProductVariant());
			transactionDetail.setQty(cart.getQty());
			transactionDetail.setPrice(cart.getProductVariant().getPrice());

			transactionDetails.add(transactionDetail);
		}

		return save(transactionDetails, user);
	}

	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}

	public BigDecimal totalTransactionAmmount() {
		List<Transaction> transactions = findAll();

		BigDecimal totalAmount = transactions.stream()
				.map(Transaction::getTotalAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return totalAmount;
	}

	public long count() {
		return transactionRepository.count();
	}

	public List<Transaction> latest(Integer number) {
		return em.createQuery("SELECT t FROM Transaction t ORDER BY t.createdDate DESC", Transaction.class)
				.setMaxResults(number)
				.getResultList();
	}
}
