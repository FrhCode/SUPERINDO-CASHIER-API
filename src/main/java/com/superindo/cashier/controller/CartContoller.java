package com.superindo.cashier.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.exception.ResourceNotFoundException;
import com.superindo.cashier.model.Cart;
import com.superindo.cashier.model.ProductVariant;
import com.superindo.cashier.model.User;
import com.superindo.cashier.request.CreateCartRequest;
import com.superindo.cashier.request.UpdateCartRequest;
import com.superindo.cashier.response.MessageResponse;
import com.superindo.cashier.service.CartService;
import com.superindo.cashier.service.JwtService;
import com.superindo.cashier.service.ProductVariantService;
import com.superindo.cashier.service.UserService;

import com.superindo.cashier.response.BaseResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartContoller {
	private final CartService cartService;
	private final ProductVariantService productVariantService;
	private final JwtService jwtService;
	private final UserService userService;

	@GetMapping
	public ResponseEntity<Object> index(HttpServletRequest request) {

		User user = jwtService.getUser(request);

		List<Cart> carts = cartService.findByUser(user);

		return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<Cart>(carts));
	}

	@PostMapping
	public ResponseEntity<Object> save(HttpServletRequest request,
			@Valid @RequestBody CreateCartRequest createCartRequest) {

		final String authHeader = request.getHeader("Authorization");
		final String jwt = authHeader.substring(7);
		String username = jwtService.extractUsername(jwt);

		User user = userService.findByEmail(username);

		Optional<ProductVariant> optionalProductVariant = productVariantService
				.findById(createCartRequest.getProductVariantId());

		if (optionalProductVariant.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		cartService.save(user, optionalProductVariant.get(), createCartRequest);

		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<String>("success"));
	}

	@PutMapping("{id}")
	public ResponseEntity<Object> update(@PathVariable Long id,
			@Valid @RequestBody UpdateCartRequest request) {

		Optional<Cart> optionalCart = cartService.findById(id);

		if (optionalCart.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		cartService.save(optionalCart.get(), request);

		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<String>("success"));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {

		Optional<Cart> optionalCart = cartService.findById(id);

		if (optionalCart.isEmpty()) {
			throw new ResourceNotFoundException();
		}

		cartService.delete(optionalCart.get());

		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse<String>("success"));
	}
}

// tes