package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.hongsi.martholidayalarm.utils.HtmlParser;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NobrandCrawler extends EmartCommonCrawler {

    private static final String DETAIL_VIEW_URL_FORMAT = "https://store.emart.com/branch/view.do?id=%s";
    private static final String CSS_SELECTOR_PHONE_NUMBER = ".intro-wrap > ul > li:nth-child(3) > p";

    @Override
    public List<Crawlable> crawl() {
        List<EmartData> marts = crawl(SearchType.NOBRAND);
        return marts.stream()
                .parallel()
                .peek(this::setPhoneNumber)
                .collect(Collectors.toList());
    }

    private void setPhoneNumber(EmartData emartData) {
        Document detail = HtmlParser.get(String.format(DETAIL_VIEW_URL_FORMAT, emartData.getRealId()));
        emartData.setPhoneNumber(detail.select(CSS_SELECTOR_PHONE_NUMBER).text());
    }
}
