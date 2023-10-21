package com.superindo.cashier.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class LoginRequest {
	@NotBlank(message = "email can not be empty")
	private String email;
	@NotBlank(message = "password can not be empty")
	private String password;
}