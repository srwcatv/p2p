package com.catv.p2p.base.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class QueryObject {
	private Integer currentPage = 1;
	private Integer pageSize = 10;

	public int getStart() {
		return (currentPage - 1) * pageSize;
	}
}
