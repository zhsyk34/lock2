package com.dnake.kit;

import java.util.List;

public class StringKit {

	public static String firstToLower(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String firstToUpper(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String from(List<String> list) {
		if (ValidateKit.empty(list)) {
			return null;
		}
		String str = list.toString();
		return str.substring(1, str.length() - 1);
	}

	public static String fuzzy(String str) {
		if (ValidateKit.empty(str)) {
			return null;
		}
		return "%" + str + "%";
	}

}
