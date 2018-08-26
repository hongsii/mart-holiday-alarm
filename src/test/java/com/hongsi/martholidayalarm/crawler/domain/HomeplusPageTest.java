package com.hongsi.martholidayalarm.crawler.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.crawler.util.CrawlingValidationUtil;
import org.junit.Test;

public class HomeplusPageTest {

	@Test
	public void getHomeplusPage() throws Exception {
		String realId = "0028";
		String url = "http://corporate.homeplus.co.kr/STORE/HyperMarket_view.aspx?sn=" + realId
				+ "&ind=HOMEPLUS";
		MartType martType = MartType.HOMEPLUS;
		HomeplusPage homeplusPage = new HomeplusPage(martType, url);

		Mart mart = homeplusPage.getInfo();

		assertThat(mart).isNotNull();
		assertThat(mart.getMartType()).isEqualTo(martType);
		assertThat(mart.getRealId()).isNotBlank().isEqualTo(realId);
		assertThat(mart.getRegion()).isNotBlank().isEqualTo("부산");
		assertThat(mart.getBranchName()).isNotBlank().isEqualTo("가야점");
		assertThat(CrawlingValidationUtil.isValidPhoneNumber(mart.getPhoneNumber())).isTrue();
		assertThat(mart.getAddress()).isNotBlank().isEqualTo("부산광역시 부산진구 가야대로 506");
		assertThat(mart.getOpeningHours()).isNotBlank();
		assertThat(mart.getUrl()).isNotBlank().isEqualTo(url);
		assertThat(mart.getHolidays()).isNotEmpty();
	}

	@Test
	public void getHomeplusPageForExpress() throws Exception {
		String realId = "0293";
		String url = "http://corporate.homeplus.co.kr/STORE/HyperMarket_Express_view.aspx?sn="
				+ realId + "&ind=EXPRESS";
		MartType martType = MartType.HOMEPLUS_EXPRESS;
		HomeplusPage homeplusPage = new HomeplusPage(martType, url);

		Mart mart = homeplusPage.getInfo();

		assertThat(mart).isNotNull();
		assertThat(mart.getMartType()).isEqualTo(martType);
		assertThat(mart.getRealId()).isNotBlank().isEqualTo(realId);
		assertThat(mart.getRegion()).isNotBlank().isEqualTo("서울");
		assertThat(mart.getBranchName()).isNotBlank().isEqualTo("가산점");
		assertThat(CrawlingValidationUtil.isValidPhoneNumber(mart.getPhoneNumber())).isTrue();
		assertThat(mart.getAddress()).isNotBlank().isEqualTo("서울특별시 금천구 가산로 106, 112");
		assertThat(mart.getOpeningHours()).isNotBlank();
		assertThat(mart.getUrl()).isNotBlank().isEqualTo(url);
		assertThat(mart.getHolidays()).isNotEmpty();
	}
}