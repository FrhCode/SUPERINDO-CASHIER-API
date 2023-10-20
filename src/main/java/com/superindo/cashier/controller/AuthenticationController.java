package com.superindo.cashier.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.superindo.cashier.request.LoginRequest;
import com.superindo.cashier.request.RegisterRequest;
import com.superindo.cashier.response.AuthenticationResponse;
import com.superindo.cashier.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
		return ResponseEntity.ok(authenticationService.register(registerRequest));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
	}
}