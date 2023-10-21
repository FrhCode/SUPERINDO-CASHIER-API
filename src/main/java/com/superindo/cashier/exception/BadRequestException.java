package com.superindo.cashier.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException {
	Map<String, Object> responseBody = new HashMap<>();
	Map<String, String> errors = new HashMap<>();

	public BadRequestException(Map<String, String> errors) {
		this.errors = errors;
		responseBody.put("message", "Validation failed");
		responseBody.put("errors", this.errors);
		responseBody.put("timestamp", ZonedDateTime.now().toString());
	}
}
