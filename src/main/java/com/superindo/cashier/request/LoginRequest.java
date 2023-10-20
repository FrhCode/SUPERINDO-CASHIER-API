package com.superindo.cashier.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data

@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message = "email can not be empty")
	private String email;
	@NotBlank(message = "password can not be empty")
	private String password;
}