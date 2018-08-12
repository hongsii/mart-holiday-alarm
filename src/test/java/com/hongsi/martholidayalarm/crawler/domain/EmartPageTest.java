package com.hongsi.martholidayalarm.crawler.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import org.junit.Test;

public class EmartPageTest {

	@Test
	public void getInfo() throws Exception {
		String realId = "1091";
		EmartPage page = new EmartPage(EmartPage.BASE_URL + "/branch/view.do?id=" + realId);
		Mart mart = page.getInfo();
		assertThat(mart).isNotNull();
		assertThat(mart.getRealId()).isNotBlank().isEqualTo(realId);
		assertThat(mart.getBranchName()).isNotBlank();
		assertThat(mart.getPhoneNumber()).isNotBlank();
		assertThat(mart.getAddress()).isNotBlank();
		assertThat(mart.getOpeningHours()).isNotBlank();
		assertThat(mart.getRegion()).isNotEmpty();
	}
}