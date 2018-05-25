package com.hongsi.martholidayalarm.crawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CrawlUtil {

	private static final Pattern replacingRegionPattern = Pattern.compile("(경상|충청|전라)(남도|북도)");

	private CrawlUtil() {
	}

	public static String getRegionFromAddress(String address) {
		String region = address.substring(0, 2);
		Matcher matcher = replacingRegionPattern.matcher(address);
		if (matcher.find()) {
			region = matcher.group(1).substring(0, 1) + matcher.group(2).substring(0, 1);
		}
		return region;
	}
}
