package com.hongsi.martholidayalarm.domain.crawler.mart;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.hongsi.martholidayalarm.domain.crawler.MartCrawler;
import com.hongsi.martholidayalarm.domain.mart.Crawlable;
import com.hongsi.martholidayalarm.utils.JsonParser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CostcoCrawler implements MartCrawler {

	private static final String CRAWL_URL = "https://www.costco.co.kr/store-finder/search?q=Korea%2C+Republic+of&page=0";
	private static final String JSON_DATA_KEY = "data";

	@Override
	public List<Crawlable> crawl() {
		JsonNode martInfo = JsonParser.parse(CRAWL_URL);
		return JsonParser.convert(martInfo.get(JSON_DATA_KEY), new TypeReference<List<CostcoData>>(){});
	}
}