package com.hongsi.martholidayalarm.crawler.model;

import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.location.Location;
import com.hongsi.martholidayalarm.core.mart.Mart;
import com.hongsi.martholidayalarm.core.mart.MartType;
import com.hongsi.martholidayalarm.crawler.domain.InvalidCrawledMart;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.TreeSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CrawledMart {

    private MartType martType;
    private String realId;
    private String branchName;
    private String region;
    private String phoneNumber;
    private String address;
    private String openingHours;
    private String url;
    private Location location;
    private String holidayText;
    private List<Holiday> holidays;

    @Builder
    public CrawledMart(MartType martType, String realId, String branchName, String region,
                       String phoneNumber, String address, String openingHours, String url,
                       Location location, String holidayText, List<Holiday> holidays) {
        this.martType = martType;
        this.realId = realId;
        this.branchName = branchName;
        this.region = region;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.openingHours = openingHours;
        this.url = url;
        this.location = location;
        this.holidayText = holidayText;
        this.holidays = holidays;
    }

    public static CrawledMart parse(MartParser parser) {
        return CrawledMart.builder()
                .martType(parser.getMartType())
                .realId(parser.getRealId())
                .branchName(parser.getBranchName())
                .region(parser.getRegion())
                .phoneNumber(parser.getPhoneNumber())
                .address(parser.getAddress())
                .openingHours(parser.getOpeningHours())
                .url(parser.getUrl())
                .location(parser.getLocation())
                .holidayText(parser.getHolidayText())
                .holidays(parser.getHolidays())
                .build();
    }

    public boolean hasLocation() {
        return location != null;
    }

    public void setLocation(Double latitude, Double longitude) {
        this.location = Location.of(latitude, longitude);
    }

    public boolean canCrawl(List<InvalidCrawledMart> invalidCrawledMarts) {
        boolean isInvalid = invalidCrawledMarts.stream()
                .anyMatch(invalidCrawledMart -> invalidCrawledMart.isInvalid(martType, realId));
        return !isInvalid;
    }

    public Mart toEntity() {
        return Mart.builder()
                .martType(martType)
                .realId(realId)
                .branchName(branchName)
                .region(region)
                .phoneNumber(phoneNumber)
                .address(address)
                .openingHours(openingHours)
                .url(url)
                .location(location)
                .holidayText(holidayText)
                .holidays(new TreeSet<>(holidays))
                .build();
    }
}
