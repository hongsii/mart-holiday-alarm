package com.hongsi.martholidayalarm.crawler.model.emart;

import com.hongsi.martholidayalarm.crawler.model.MartParser;
import com.hongsi.martholidayalarm.crawler.utils.HtmlParser;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NobrandCrawler extends EmartCommonCrawler {

    private static final String DETAIL_VIEW_URL_FORMAT = "https://store.emart.com/branch/view.do?id=%s";
    private static final String CSS_SELECTOR_PHONE_NUMBER = ".intro-wrap > ul > li:nth-child(3) > p";

    @Override
    public List<MartParser> crawl() {
        List<MartParser> marts = super.crawl();
        return marts.stream()
                .parallel()
                .peek(this::setPhoneNumber)
                .collect(Collectors.toList());
    }

    @Override
    protected SearchType getSearchType() {
        return SearchType.NOBRAND;
    }

    private void setPhoneNumber(MartParser martParser) {
        Document detail = HtmlParser.get(String.format(DETAIL_VIEW_URL_FORMAT, martParser.getRealId()));
        ((EmartParser) martParser).setPhoneNumber(detail.select(CSS_SELECTOR_PHONE_NUMBER).text());
    }
}
