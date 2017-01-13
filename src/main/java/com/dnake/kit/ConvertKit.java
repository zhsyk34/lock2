package com.dnake.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ConvertKit {

	public static LocalDateTime strToDateTime(String str, String pattern) {
		if (ValidateKit.empty(str)) {
			return null;
		}
		if (ValidateKit.empty(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}

		try {
			return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
		} catch (Exception e) {
			return null;
		}
	}

	public static LocalDateTime strToDateTime(String str) {
		return strToDateTime(str, null);
	}

	public static Date normalToDateTime(String str) {
		if (str.isEmpty())
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
