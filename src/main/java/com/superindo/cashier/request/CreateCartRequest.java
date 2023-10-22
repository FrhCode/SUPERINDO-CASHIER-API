package com.superindo.cashier.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartRequest {
	@NotNull(message = "product_variant_id tidak boleh kosong")
	private Long productVariantId;

	@NotNull(message = "Qty tidak boleh kosong")
	private Long qty;
}
