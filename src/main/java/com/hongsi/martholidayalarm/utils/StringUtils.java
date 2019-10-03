package com.hongsi.martholidayalarm.utils;

public class StringUtils {

	public static String replaceWhitespace(String value) {
		return value.replaceAll("\\s+", "");
	}

	public static boolean isBlank(String value) {
		return value == null || value.trim().isEmpty();
	}
}
