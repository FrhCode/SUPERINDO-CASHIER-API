package com.superindo.cashier.service;

import org.springframework.stereotype.Service;

import com.superindo.cashier.model.User;
import com.superindo.cashier.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public User findByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
}
