package com.superindo.cashier.auditor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.superindo.cashier.model.User;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// return (String) authentication.getPrincipal().;
		// Object principal = ;

		if (authentication == null) {
			return Optional.of("administrator");
		}

		User principal = (User) authentication.getPrincipal();

		return Optional.of(principal.getName());
	}
}