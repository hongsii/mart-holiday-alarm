package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import com.ibm.icu.util.ChineseCalendar;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@UtilityClass
public class SolarLunarConverter {

	public static LocalDate convertLunarToSolar(LocalDate lunar) {
		ChineseCalendar cc = new ChineseCalendar();

		cc.set(ChineseCalendar.EXTENDED_YEAR, lunar.getYear() + 2637);
		cc.set(ChineseCalendar.MONTH, lunar.getMonthValue() - 1);
		cc.set(ChineseCalendar.DAY_OF_MONTH, lunar.getDayOfMonth());

		return Instant.ofEpochMilli(cc.getTimeInMillis())
				.atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
