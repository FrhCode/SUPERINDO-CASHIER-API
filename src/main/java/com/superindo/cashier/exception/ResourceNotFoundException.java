package com.superindo.cashier.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
	Map<String, String> responseBody = new HashMap<>();

	public ResourceNotFoundException() {
		this("Resource Not Found");
	}

	public ResourceNotFoundException(String message) {
		responseBody.put("message", message);
		responseBody.put("timestamp", ZonedDateTime.now().toString());
	}

}
