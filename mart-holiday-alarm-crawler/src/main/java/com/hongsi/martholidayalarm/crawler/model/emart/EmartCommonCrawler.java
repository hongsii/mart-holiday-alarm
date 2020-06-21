package com.hongsi.martholidayalarm.crawler.model.emart;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongsi.martholidayalarm.crawler.MartCrawler;
import com.hongsi.martholidayalarm.crawler.MartCrawlerType;
import com.hongsi.martholidayalarm.crawler.exception.CrawlerDataParseException;
import com.hongsi.martholidayalarm.crawler.model.MartParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class EmartCommonCrawler implements MartCrawler {

    private static final String HOLIDAY_URL = MartCrawlerType.EMART.appendUrl("/branch/holidayList.do?year=%d&month=%02d");
    private static final int MAX_ADDITION_MONTH_COUNT = 2;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public EmartCommonCrawler() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<MartParser> crawl() {
        List<EmartParser> martParsers = parseMartParsers();
        List<EmartHolidayParser> holidayParsers = parseHolidayParsers();
        return martParsers.stream()
                .peek(parser -> parser.addHolidays(holidayParsers))
                .collect(Collectors.toList());
    }

    protected abstract SearchType getSearchType();

    private List<EmartParser> parseMartParsers() {
        String url = MartCrawlerType.EMART.appendUrl("/branch/searchList.do");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("jMode", "true");
        param.add("searchType", getSearchType().value);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(param, headers);

        String body = restTemplate.postForObject(url, entity, String.class);
        return parseBody(body, DataResponse.class).dataList;
    }

    private List<EmartHolidayParser> parseHolidayParsers() {
        List<EmartHolidayParser> holidayParsers = new ArrayList<>();
        YearMonth current = YearMonth.now();
        YearMonth end = current.plusMonths(MAX_ADDITION_MONTH_COUNT);
        while (!end.isBefore(current)) {
            String url = String.format(HOLIDAY_URL, current.getYear(), current.getMonthValue());
            String body = restTemplate.getForObject(url, String.class);
            HolidayResponse response = parseBody(body, HolidayResponse.class);
            holidayParsers.addAll(response.dateList);
            current = current.plusMonths(1);
        }
        return holidayParsers;
    }

    private <T> T parseBody(String body, Class<T> responseClass) {
        try {
            return objectMapper.readValue(body, responseClass);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CrawlerDataParseException(e);
        }
    }

    @AllArgsConstructor
    protected enum SearchType {

        EMART("EM"), TRADERS("TR"), NOBRAND("NO");

        private final String value;
    }

    @Data
    @NoArgsConstructor
    public static class DataResponse {

        private List<EmartParser> dataList;
    }

    @Data
    @NoArgsConstructor
    public static class HolidayResponse {

        private List<EmartHolidayParser> dateList;
    }
}