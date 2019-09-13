package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.MartCrawler;
import com.hongsi.martholidayalarm.utils.JsonParser;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmartCrawler implements MartCrawler {

	private static final String MART_URL = CrawlerMartType.EMART.appendUrl("/branch/searchList.do");
	private static final String HOLIDAY_URL_FORMAT = CrawlerMartType.EMART
			.appendUrl("/branch/holidayList.do?year=%d&month=%02d");
	private static final String MART_DATA_KEY = "dataList";
	private static final String HOLIDAY_DATA_KEY = "dateList";
	private static final int MAX_ADDITION_MONTH = 2;
	private static final int ADD_MONTH = 1;

	@Override
	public List<Crawlable> crawl() {
		List<EmartData> martData = parseMartData();

		List<EmartHolidayData> holidayData = parseHolidayDataFromYearMonth();
		martData.stream()
				.forEach(data -> data.addHolidays(holidayData));

		return martData.stream()
				.filter(EmartData::isValidTarget)
				.collect(Collectors.toList());
	}

	public List<EmartData> parseMartData() {
		JsonNode martInfo = JsonParser.parse(MART_URL);
		return JsonParser.convert(martInfo.get(MART_DATA_KEY), new TypeReference<List<EmartData>>() {});
	}

	private List<EmartHolidayData> parseHolidayDataFromYearMonth() {
		List<EmartHolidayData> holidayData = new ArrayList<>();
		for (YearMonth current = YearMonth.now(), end = current.plusMonths(MAX_ADDITION_MONTH);
				!end.isBefore(current); current = current.plusMonths(ADD_MONTH)) {
			String url = String
					.format(HOLIDAY_URL_FORMAT, current.getYear(), current.getMonthValue());
			Optional.ofNullable(parseHolidayData(url))
					.ifPresent(data -> holidayData.addAll(data));
		}
		return holidayData;
	}

	private List<EmartHolidayData> parseHolidayData(String url) {
		JsonNode holidayNode = JsonParser.parse(url).get(HOLIDAY_DATA_KEY);
		return JsonParser.convert(holidayNode, new TypeReference<List<EmartHolidayData>>() {
		});
	}
}
