package com.superindo.cashier.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginateProductVariantRequest extends Paginate {
	private Long productId;
}
