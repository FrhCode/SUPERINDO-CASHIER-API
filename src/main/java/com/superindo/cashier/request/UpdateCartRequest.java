package com.superindo.cashier.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartRequest {

	@NotNull(message = "Qty tidak boleh kosong")
	private Long qty;
}
