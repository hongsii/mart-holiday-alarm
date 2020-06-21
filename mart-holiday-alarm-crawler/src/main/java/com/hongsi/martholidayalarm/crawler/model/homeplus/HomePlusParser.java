package com.hongsi.martholidayalarm.crawler.model.homeplus;

import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.location.Location;
import com.hongsi.martholidayalarm.core.mart.MartType;
import com.hongsi.martholidayalarm.crawler.exception.PageNotFoundException;
import com.hongsi.martholidayalarm.crawler.model.MartParser;
import com.hongsi.martholidayalarm.crawler.utils.HtmlParser;
import com.hongsi.martholidayalarm.crawler.utils.MatchSpliterator;
import com.hongsi.martholidayalarm.crawler.utils.RegionParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.hongsi.martholidayalarm.crawler.model.homeplus.HomePlusCommonCrawler.StoreType;

@Slf4j
public class HomePlusParser implements MartParser {

    private static final Pattern MART_TYPE_PATTERN = Pattern.compile("(?!&ind=)\\w+$");
    private static final Pattern REAL_ID_PATTERN = Pattern.compile("(?!sn=)\\d+(?=&)?");
    private static final Pattern HOLIDAY_PATTERN = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
    private static final String REGULAR_HOLIDAY_SELECTOR = ".time > span.off";
    private static final String BRANCH_NAME_SELECTOR = ".type > .name";
    private static final String TABLE_SELECTOR = ".table.table-bordered > tbody > tr > td";
    private static final int ADDRESS_ROW_INDEX = 0;
    private static final int HOLIDAY_ROW_INDEX = 1;
    private static final int PHONE_NUMBER_ROW_INDEX = 2;
    private static final int OPENING_HOURS_ROW_INDEX = 3;

    private final String url;
    private final Document page;

    public HomePlusParser(String url) {
        this.url = url;
        this.page = parsePage(url);
    }

    private Document parsePage(String url) {
        Document page = HtmlParser.get(url);
        if (page.body().text().isEmpty()) {
            throw new PageNotFoundException();
        }
        return page;
    }

    @Override
    public MartType getMartType() {
        return MatchSpliterator.from(MART_TYPE_PATTERN.matcher(url))
                .stream()
                .findFirst()
                .map(storeType -> StoreType.of(storeType).getMartType())
                .orElseThrow(() -> new IllegalArgumentException("마트타입을 찾을 수 없습니다."));
    }

    @Override
    public String getRealId() {
        return MatchSpliterator.from(REAL_ID_PATTERN.matcher(url))
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("고유 id가 존재하지 않습니다."));
    }

    @Override
    public String getBranchName() {
        return page.select(BRANCH_NAME_SELECTOR).text();
    }

    @Override
    public String getRegion() {
        return RegionParser.getRegionFromAddress(getAddress());
    }

    @Override
    public String getPhoneNumber() {
        return page.select(TABLE_SELECTOR).eq(PHONE_NUMBER_ROW_INDEX).text();
    }

    @Override
    public String getAddress() {
        return page.select(TABLE_SELECTOR).eq(ADDRESS_ROW_INDEX).text();
    }

    @Override
    public String getOpeningHours() {
        return page.select(TABLE_SELECTOR).eq(OPENING_HOURS_ROW_INDEX).text();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public String getHolidayText() {
        return page.select(REGULAR_HOLIDAY_SELECTOR).text();
    }

    @Override
    public List<Holiday> getHolidays() {
        List<Holiday> holidays = parseHolidays();
        return !holidays.isEmpty() ? holidays : generateRegularHolidays(getHolidayText());
    }

    private List<Holiday> parseHolidays() {
        String rawHolidays = page.select(TABLE_SELECTOR).eq(HOLIDAY_ROW_INDEX).text();
        Matcher matcher = HOLIDAY_PATTERN.matcher(rawHolidays);
        return MatchSpliterator.from(matcher).stream()
                .map(LocalDate::parse)
                .map(Holiday::of)
                .filter(Holiday::isUpcoming)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "HomePlusParser{" +
                "martType=" + getMartType() +
                ", realId=" + getRealId() +
                ", branchName=" + getBranchName() +
                ", region=" + getRegion() +
                ", phoneNumber=" + getPhoneNumber() +
                ", address=" + getAddress() +
                ", openingHours=" + getOpeningHours() +
                ", url=" + getUrl() +
                ", holidayText=" + getHolidayText() +
                ", holidays=" + getHolidays() +
                "}";
    }
}
