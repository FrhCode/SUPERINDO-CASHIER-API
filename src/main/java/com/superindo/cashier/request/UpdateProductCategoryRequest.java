package com.superindo.cashier.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductCategoryRequest {
	@Size(min = 3, message = "Nama setidaknya terdiri dari 3 karakter")
	private String name;

	@NotNull(message = "Active tidak boleh null")
	private Boolean active;
}
