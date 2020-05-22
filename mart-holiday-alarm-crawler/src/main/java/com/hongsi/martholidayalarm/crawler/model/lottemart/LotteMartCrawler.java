package com.hongsi.martholidayalarm.crawler.model.lottemart;

import com.fasterxml.jackson.databind.JsonNode;
import com.hongsi.martholidayalarm.crawler.MartCrawler;
import com.hongsi.martholidayalarm.crawler.MartCrawlerType;
import com.hongsi.martholidayalarm.crawler.model.MartParser;
import com.hongsi.martholidayalarm.crawler.utils.HtmlParser;
import com.hongsi.martholidayalarm.crawler.utils.JsonParser;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Component
public class LotteMartCrawler implements MartCrawler {

    private static final String REGION_URL = MartCrawlerType.LOTTEMART.appendUrl("/bc/branch/holidaystore.do?SITELOC=DC009");
    private static final String REAL_ID_URL = MartCrawlerType.LOTTEMART.appendUrl("/bc/branch/regnstorelist.json?regionCode=");
    private static final String DATA_URL = MartCrawlerType.LOTTEMART.appendUrl("/bc/branch/storeinfo.json?brnchCd=");
    private static final String REGION_BUTTON_SELECTOR = "div.wrap-location-inner > button";
    private static final String REAL_ID_KEY = "brnchCd";
    private static final String DATA_KEY = "data";

    @Override
    public List<MartParser> crawl() {
        return parseRegionCode().stream()
                .flatMap(regionCode -> parseRealId(regionCode).stream())
                .distinct()
                .map(this::parseData)
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private List<String> parseRegionCode() {
        return HtmlParser.get(REGION_URL)
                .select(REGION_BUTTON_SELECTOR).stream()
                .map(Element::val)
                .collect(toList());
    }

    private List<String> parseRealId(String regionCode) {
        String url = REAL_ID_URL + regionCode;
        JsonNode dataNode = JsonParser.request(url).get(DATA_KEY);
        return dataNode.findValuesAsText(REAL_ID_KEY);
    }

    private LotteMartParser parseData(String realId) {
        String url = DATA_URL + realId;
        JsonNode dataNode = JsonParser.request(url).get(DATA_KEY);
        return JsonParser.convert(dataNode, LotteMartParser.class);
    }
}
