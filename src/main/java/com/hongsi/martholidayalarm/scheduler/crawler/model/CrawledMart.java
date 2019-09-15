package com.hongsi.martholidayalarm.scheduler.crawler.model;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.scheduler.crawler.domain.InvalidMart;
import com.hongsi.martholidayalarm.scheduler.crawler.model.mart.Crawlable;
import lombok.*;

import java.util.List;
import java.util.TreeSet;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CrawledMart {

	private MartType martType;
	private String realId;
	private String branchName;
	private String region;
	private String phoneNumber;
	private String address;
	private String openingHours;
	private String url;
	@Setter
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

	public static CrawledMart parse(Crawlable crawlable) {
		return CrawledMart.builder()
				.martType(crawlable.getMartType())
				.realId(crawlable.getRealId())
				.branchName(crawlable.getBranchName())
				.region(crawlable.getRegion())
				.phoneNumber(crawlable.getPhoneNumber())
				.address(crawlable.getAddress())
				.openingHours(crawlable.getOpeningHours())
				.url(crawlable.getUrl())
                .location(crawlable.getLocation())
				.holidayText(crawlable.getHolidayText())
				.holidays(crawlable.getHolidays())
				.build();
	}

	public boolean hasLocation() {
		return location != null;
	}

	public boolean canCrawl(List<InvalidMart> invalidMarts) {
		boolean isInvalid = invalidMarts.stream()
				.anyMatch(invalidMart -> invalidMart.isInvalid(martType, realId));
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
