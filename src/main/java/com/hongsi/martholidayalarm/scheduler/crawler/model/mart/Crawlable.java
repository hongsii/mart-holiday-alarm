package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.holiday.RegularHolidayGenerator;

import java.util.Collections;
import java.util.List;

public interface Crawlable {

	MartType getMartType();
	String getRealId();
	String getBranchName();
	String getRegion();
	String getPhoneNumber();
	String getAddress();
	String getOpeningHours();
	String getUrl();
	Location getLocation();
	String getHolidayText();
	List<Holiday> getHolidays();

	default List<Holiday> generateRegularHoliday(String holidayText) {
		try {
			RegularHolidayGenerator generator = RegularHolidayGenerator.parse(holidayText);
			return generator.generate();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}
