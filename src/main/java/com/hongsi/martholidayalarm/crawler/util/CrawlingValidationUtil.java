package com.hongsi.martholidayalarm.crawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlingValidationUtil {

	public static final Pattern PHONE_NUMBER_PATTERN = Pattern
			.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$");

	public static boolean isValidPhoneNumber(String phoneNumber) {
		Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
		return matcher.matches();
	}
}
