package com.superindo.cashier.request;

import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
abstract class Paginate {
	private String query = "";
	private Integer page = 1;
	private Integer size = 25;
	private String sortBy = "id";
	private Sort.Direction sortDirection = Sort.Direction.ASC;

	public void setSortBy(String sortBy) {
		if (!sortBy.isEmpty()) {
			this.sortBy = sortBy;
		}
	}
}
