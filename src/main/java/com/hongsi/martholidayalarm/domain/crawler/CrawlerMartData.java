package com.hongsi.martholidayalarm.domain.crawler;

import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import java.util.List;
import java.util.TreeSet;
import lombok.Builder;
import lombok.Data;

@Data
public class CrawlerMartData {

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
	public CrawlerMartData(MartType martType, String realId, String branchName, String region,
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

	public void setAddressInfo(LocationConvertResult locationConvertResult) {
		address = locationConvertResult.getAddress(address);
		location = locationConvertResult.getLocation();
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
