package com.hongsi.martholidayalarm.crawler.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.crawler.util.CrawlingValidationUtil;
import java.io.IOException;
import org.junit.Test;

public class HomeplusPageTest {

	@Test
	public void getHomeplusPage() throws IOException {
		String realId = "0028";
		String url = "http://corporate.homeplus.co.kr/STORE/HyperMarket_view.aspx?sn=" + realId
				+ "&ind=HOMEPLUS";
		HomeplusPage homeplusPage = new HomeplusPage(MartType.HOMEPLUS, url);

		Mart mart = homeplusPage.getInfo();

		assertThat(mart).isNotNull();
		assertThat(mart.getMartType()).isEqualTo(MartType.HOMEPLUS);
		assertThat(mart.getRealId()).isNotBlank().isEqualTo(realId);
		assertThat(mart.getRegion()).isNotBlank().isEqualTo("부산");
		assertThat(mart.getBranchName()).isNotBlank().isEqualTo("가야점");
		assertThat(CrawlingValidationUtil.isValidPhoneNumber(mart.getPhoneNumber())).isTrue();
		assertThat(mart.getAddress()).isNotBlank().isEqualTo("부산광역시 부산진구 가야대로 506");
		assertThat(mart.getOpeningHours()).isNotBlank();
		assertThat(mart.getUrl()).isNotBlank().isEqualTo(url);
		assertThat(mart.getHolidays()).isNotEmpty();
	}
}