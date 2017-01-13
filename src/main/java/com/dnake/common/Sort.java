package com.dnake.common;

public class Sort {

	private static final String DEFAULT_COLUMN = "id";
	private static final Order DEFAULT_ORDER = Order.DESC;

	private final String column;

	private final Order order;

	private Sort(String column, Order order) {
		this.column = column;
		this.order = order;
	}

	public static Sort of(String column, Order order) {
		column = (column == null || column.isEmpty()) ? DEFAULT_COLUMN : column;
		order = order == null ? DEFAULT_ORDER : order;

		return new Sort(column, order);
	}

	public String column() {
		return column;
	}

	public Order order() {
		return order;
	}
}
