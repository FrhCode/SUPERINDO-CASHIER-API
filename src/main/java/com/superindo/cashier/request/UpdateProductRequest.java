package com.superindo.cashier.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {
	@Size(min = 3, message = "Nama setidaknya terdiri dari 3 karakter")
	private String name;

	@NotNull(message = "active tidak boleh kosong")
	private Boolean active;

	@NotBlank(message = "thumbnail tidak boleh kosong")
	private String thumbnail;
}
