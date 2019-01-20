package com.hongsi.martholidayalarm.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void 공백_제거() {
		assertThat(StringUtils.replaceWhitespace("공백 문자")).isEqualTo("공백문자");
		assertThat(StringUtils.replaceWhitespace("공백 여러개   문자")).isEqualTo("공백여러개문자");
	}
}