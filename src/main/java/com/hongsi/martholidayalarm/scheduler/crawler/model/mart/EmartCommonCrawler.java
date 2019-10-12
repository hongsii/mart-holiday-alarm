package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.MartCrawler;
import com.hongsi.martholidayalarm.utils.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Slf4j
@Component
public abstract class EmartCommonCrawler implements MartCrawler {

    private static final String MART_URL = CrawlerMartType.EMART.appendUrl("/branch/searchList.do?searchType=%s");
    private static final String HOLIDAY_URL = CrawlerMartType.EMART.appendUrl("/branch/holidayList.do?year=%d&month=%02d");
    private static final String MART_DATA_KEY = "dataList";
    private static final String HOLIDAY_DATA_KEY = "dateList";
    private static final int MAX_ADDITION_MONTH = 2;

    protected List<EmartData> crawl(SearchType searchType) {
        String url = String.format(MART_URL, searchType.value);
        List<EmartData> marts = parseMartFromJson(url);
        List<EmartHolidayData> holidays = parseHolidayDataFromYearMonth();
        marts.forEach(emartData -> emartData.addHolidays(holidays));
        return marts;
    }

    private List<EmartData> parseMartFromJson(String url) {
        JsonNode martInfo = JsonParser.parse(url);
        List<EmartData> list = JsonParser.convert(martInfo.get(MART_DATA_KEY), new TypeReference<List<EmartData>>() {});
        return (list != null) ? list : emptyList();
    }

    private List<EmartHolidayData> parseHolidayDataFromYearMonth() {
        List<EmartHolidayData> holidayData = new ArrayList<>();
        for (YearMonth current = YearMonth.now(), end = current.plusMonths(MAX_ADDITION_MONTH);
             !end.isBefore(current);
             current = current.plusMonths(1)) {
            String url = String.format(HOLIDAY_URL, current.getYear(), current.getMonthValue());
            holidayData.addAll(parseHolidayDataFromJson(url));
        }
        return holidayData;
    }

    private List<EmartHolidayData> parseHolidayDataFromJson(String url) {
        JsonNode holidayNode = JsonParser.parse(url);
        List<EmartHolidayData> list = JsonParser.convert(holidayNode.get(HOLIDAY_DATA_KEY), new TypeReference<List<EmartHolidayData>>() {});
        return (list != null) ? list : emptyList();
    }

    @RequiredArgsConstructor
    protected enum SearchType {

        EMART("EM"),
        TRADERS("TR");

        private final String value;
    }
}