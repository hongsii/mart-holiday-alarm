package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.crawler.domain.holiday.RegularHolidayGenerator;
import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.MartData;
import java.util.Collections;
import java.util.List;

public abstract class CrawlerMartData implements MartData {

	protected List<Holiday> generateRegularHoliday(String holidayText) {
		try {
			RegularHolidayGenerator generator = RegularHolidayGenerator.parse(holidayText);
			return generator.generate();
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public String toString() {
		return "MartData{" +
				", martType=" + getMartType() +
				", realId='" + getRealId() + '\'' +
				", branchName='" + getBranchName() + '\'' +
				", region='" + getRegion() + '\'' +
				", phoneNumber='" + getPhoneNumber() + '\'' +
				", address='" + getAddress() + '\'' +
				", openingHours='" + getOpeningHours() + '\'' +
				", holidays=" + getHolidays() +
				'}';
	}
}
