package com.superindo.cashier.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginateProductVariantRequest extends Paginate {
	@NotNull
	private Long productId;
}
