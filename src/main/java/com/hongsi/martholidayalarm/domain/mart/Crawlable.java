package com.hongsi.martholidayalarm.domain.mart;

import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartData;
import com.hongsi.martholidayalarm.domain.crawler.holiday.RegularHolidayGenerator;
import java.util.Collections;
import java.util.List;

public interface Crawlable {

	default List<Holiday> generateRegularHoliday(String holidayText) {
		try {
			RegularHolidayGenerator generator = RegularHolidayGenerator.parse(holidayText);
			return generator.generate();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	default CrawlerMartData toData() {
		return CrawlerMartData.builder()
				.martType(getMartType())
				.realId(getRealId())
				.branchName(getBranchName())
				.region(getRegion())
				.phoneNumber(getPhoneNumber())
				.address(getAddress())
				.openingHours(getOpeningHours())
				.url(getUrl())
				.holidays(getHolidays())
				.build();
	}

	MartType getMartType();
	String getRealId();
	String getBranchName();
	String getRegion();
	String getPhoneNumber();
	String getAddress();
	String getOpeningHours();
	String getUrl();
	Location getLocation();
	List<Holiday> getHolidays();
}
