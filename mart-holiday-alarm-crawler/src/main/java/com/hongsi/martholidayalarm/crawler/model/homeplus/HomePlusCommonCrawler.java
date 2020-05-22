package com.hongsi.martholidayalarm.crawler.model.homeplus;

import com.hongsi.martholidayalarm.core.mart.MartType;
import com.hongsi.martholidayalarm.crawler.MartCrawler;
import com.hongsi.martholidayalarm.crawler.MartCrawlerType;
import com.hongsi.martholidayalarm.crawler.exception.PageNotFoundException;
import com.hongsi.martholidayalarm.crawler.model.MartParser;
import com.hongsi.martholidayalarm.crawler.utils.HtmlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public abstract class HomePlusCommonCrawler implements MartCrawler {

    private static final String LIST_URL = MartCrawlerType.HOMEPLUS.appendUrl("/STORE/HyperMarket.aspx");
    private static final String MART_LINK_SELECTOR = ".type > .name > a";
    private static final String MART_LINK_ATTR_KEY = "href";

    @Override
    public List<MartParser> crawl() {
        return HtmlParser.post(LIST_URL, getRequestParams())
                .select(MART_LINK_SELECTOR).stream()
                .map(element -> element.attr(MART_LINK_ATTR_KEY).trim())
                .map(this::parseMartData)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        params.put("__EVENTTARGET", "");
        params.put("__EVENTARGUMENT", "");
        params.put("__LASTFOCUS", "");
        params.put("__VIEWSTATE",
                "/wEPDwUJLTc2MDkzMDI3D2QWAmYPZBYCAgUPZBYCAgEPZBYCAgEPEGRkFgFmZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAwUkY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRzdG9yZXR5cGUxBSRjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHN0b3JldHlwZTIFJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkc3RvcmV0eXBlM+aYO9PJofU5uQQJJZRZ2bboir3I");
        params.put("ctl00$ContentPlaceHolder1$Region_Code", "");
        params.put("ctl00$ContentPlaceHolder1$srch_name", "");
        params.put("ctl00$ContentPlaceHolder1$Button1", "");
        params.put("ctl00$ContentPlaceHolder1$" + getStoreType().searchType, "on");
        return params;
    }

    protected abstract StoreType getStoreType();


    private Optional<HomePlusParser> parseMartData(String suffixUrl) {
        try {
            String url = MartCrawlerType.HOMEPLUS.appendUrl(suffixUrl);
            return Optional.of(new HomePlusParser(url));
        } catch (PageNotFoundException e) {
            log.error("failed to parse homeplus. message : {}, url : {}", e.getMessage(), suffixUrl);
            return Optional.empty();
        }
    }


    public enum StoreType {
        HOMEPLUS(MartType.HOMEPLUS, "storetype1"),
        EXPRESS(MartType.HOMEPLUS_EXPRESS, "storeType2"),
        UNKNOWN;

        private final MartType martType;
        private final String searchType;

        StoreType() {
            this(null, "");
        }

        StoreType(MartType martType, String searchType) {
            this.martType = martType;
            this.searchType = searchType;
        }

        public static StoreType of(String type) {
            return Arrays.stream(values())
                    .filter(storeType -> storeType.name().equals(type))
                    .findFirst()
                    .orElse(UNKNOWN);
        }

        public MartType getMartType() {
            return martType;
        }
    }
}
