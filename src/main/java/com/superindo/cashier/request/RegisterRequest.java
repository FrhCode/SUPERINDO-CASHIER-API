package com.superindo.cashier.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data

@NoArgsConstructor
public class RegisterRequest {
	@NotBlank(message = "Password can not be empty")

	private String username;

	@NotBlank(message = "Password can not be empty")
	private String email;

	@NotBlank(message = "Password can not be empty")
	private String password;
	// private Role role;
}
