package com.dnake.common.mapping;

public class Tuple {
	private final String field;
	private final String column;

	private Tuple(String field, String column) {
		this.field = field;
		this.column = column;
	}

	public static Tuple of(String field, String column) {
		return new Tuple(field, column);
	}

	public String field() {
		return field;
	}

	public String column() {
		return column;
	}

	@Override
	public String toString() {
		return "field='" + field + "'" + ", column='" + column + "'";
	}
}
