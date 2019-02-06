package com.hongsi.martholidayalarm.domain.crawler.mart;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.domain.crawler.AbstractMartCrawler;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartData;
import com.hongsi.martholidayalarm.utils.JsonParser;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CostcoCrawler extends AbstractMartCrawler {

	private static final String CRAWL_URL = "https://www.costco.co.kr/store-finder/search?q=Korea%2C+Republic+of&page=0";
	private static final String JSON_DATA_KEY = "data";

	public CostcoCrawler(LocationConvertClient locationConvertClient) {
		super(locationConvertClient);
	}

	@Override
	public List<CrawlerMartData> crawl() {
		return parseJsonData().stream()
				.map(super::toCrawlerMartData)
				.collect(Collectors.toList());
	}

	private List<CostcoData> parseJsonData() {
		JsonNode martInfo = JsonParser.parse(CRAWL_URL);
		return JsonParser.convert(martInfo.get(JSON_DATA_KEY), new TypeReference<List<CostcoData>>(){});
	}
}
