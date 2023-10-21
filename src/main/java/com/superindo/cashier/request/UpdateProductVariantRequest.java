package com.superindo.cashier.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductVariantRequest {

	@Size(min = 3, message = "Nama setidaknya terdiri dari 3 karakter")
	private String name;

	@NotNull(message = "Qty tidak boleh kosong")
	private Long qty;

	@NotNull(message = "harga tidak boleh kosong")
	@DecimalMin(value = "0.0", inclusive = false, message = "harga harus lebih besar dari 0")
	private BigDecimal price;

	@NotNull(message = "active tidak boleh kosong")
	private Boolean active;

	@NotBlank(message = "active tidak boleh kosong")
	private String thumbnail;

}
