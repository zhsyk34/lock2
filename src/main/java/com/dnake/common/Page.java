package com.dnake.common;

import org.apache.ibatis.session.RowBounds;

public class Page {

	private static final int DEFAULT_INDEX = 1;
	private static final int DEFAULT_SIZE = 10;

	private final int offset;
	private final int limit;

	private Page(int offset, int limit) {
		this.offset = offset;
		this.limit = limit;
	}

	public static Page of(Integer pageNo, Integer pageSize) {
		int number = (pageNo == null || pageNo < 1) ? DEFAULT_INDEX : pageNo;
		int size = (pageSize == null || pageSize < 1) ? DEFAULT_SIZE : pageSize;

		return new Page((number - 1) * size, size);
	}

	public RowBounds row() {
		return new RowBounds(this.offset, this.limit);
	}

	public int pageNo() {
		return offset / limit + 1;
	}

	public int pageSize() {
		return limit;
	}

}
