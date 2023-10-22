package com.superindo.cashier.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.superindo.cashier.model.Cart;
import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.model.User;
import com.superindo.cashier.repository.CartRepository;
import com.superindo.cashier.request.CreateCartRequest;
import com.superindo.cashier.request.UpdateCartRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cartRepository;

	public List<Cart> findByUser(User user) {
		return cartRepository.findByUser(user);
	}

	public void save(Cart cart) {
		cartRepository.save(cart);
	}

	public void save(User user, ProductVariant productVariant, CreateCartRequest request) {
		Cart cart = new Cart();
		cart.setProductVariant(productVariant);
		cart.setQty(request.getQty());
		cart.setUser(user);

		save(cart);
	}

	public Optional<Cart> findById(Long id) {
		return cartRepository.findById(id);
	}

	public void save(Cart cart, UpdateCartRequest request) {
		cart.setQty(request.getQty());
		save(cart);
	}

	public void delete(Cart cart) {
		cartRepository.delete(cart);
	}

	public void delete(List<Cart> carts) {
		cartRepository.deleteAll(carts);
	}

}
