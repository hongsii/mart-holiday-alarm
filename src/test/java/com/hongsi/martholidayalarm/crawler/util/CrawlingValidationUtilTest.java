package com.hongsi.martholidayalarm.crawler.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CrawlingValidationUtilTest {

	@Test
	public void isValidPhoneNumber() {
		String phoneNumber = "02-1234-4560";
		assertThat(CrawlingValidationUtil.isValidPhoneNumber(phoneNumber)).isTrue();
	}

	@Test
	public void isInvalidPhoneNumber() {
		String phoneNumber = "02-12-4560";
		assertThat(CrawlingValidationUtil.isValidPhoneNumber(phoneNumber)).isFalse();
	}
}