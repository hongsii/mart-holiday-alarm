package com.hongsi.martholidayalarm.crawler.util;

import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlUtilTest {

	@Test
	public void 네자리_지역명_두자리로_변경() throws Exception {
		String[] replacingRegions = {"서울광역시", "부산광역시", "경기도", "강원도", "경상북도", "경상남도"
				, "충청북도", "충청남도", "전라북도", "전라남도"};

		List<String> replacedRegions = new ArrayList<>();
		for (String region : replacingRegions) {
			String regionFromAddress = CrawlUtil.getRegionFromAddress(region);
			replacedRegions.add(regionFromAddress);
		}

		Assert.assertThat(replacedRegions.toArray(),
				is(new String[]{"서울", "부산", "경기", "강원", "경북", "경남"
						, "충북", "충남", "전북", "전남"}));
	}
}