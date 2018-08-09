package com.hongsi.martholidayalarm.crawler.domain;

import org.junit.Test;

public class LottemartPageTest {

	@Test
	public void getInfo() throws Exception {
		String realId = "0100001";
		LottemartPage page = new LottemartPage(
				LottemartPage.BASE_URL + "/branch/bc/main.do?brnchCd=" + realId);
		System.out.println();
//		Mart mart = page.getInfo();
//		assertThat(mart).isNotNull();
//		assertThat(mart.getRealId()).isNotBlank().isEqualTo(realId);
//		assertThat(mart.getBranchName()).isNotBlank();
//		assertThat(mart.getPhoneNumber()).isNotBlank();
//		assertThat(mart.getAddress()).isNotBlank();
//		assertThat(mart.getOpeningHours()).isNotBlank();
//		assertThat(mart.getRegion()).isNotEmpty();
	}
}