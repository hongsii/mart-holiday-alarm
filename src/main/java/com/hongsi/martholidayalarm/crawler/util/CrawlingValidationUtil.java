package com.hongsi.martholidayalarm.crawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlingValidationUtil {

	public static boolean isValidPhoneNumber(String phoneNumber) {
		Pattern phoneNumberPattern = Pattern.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$");
		Matcher matcher = phoneNumberPattern.matcher(phoneNumber);
		return matcher.matches();
	}
}
