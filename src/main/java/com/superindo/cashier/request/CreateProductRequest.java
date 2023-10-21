package com.superindo.cashier.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
	@Size(min = 3, message = "plu setidaknya terdiri dari 3 karakter")
	private String plu;

	@Size(min = 3, message = "Nama setidaknya terdiri dari 3 karakter")
	private String name;

	@NotNull(message = "active tidak boleh kosong")
	private Boolean active;

	@NotNull(message = "product_category_id tidak boleh kosong")
	private Long productCategoryId;
}
