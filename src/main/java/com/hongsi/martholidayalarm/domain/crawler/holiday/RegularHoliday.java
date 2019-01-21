package com.hongsi.martholidayalarm.domain.crawler.holiday;

import java.time.DayOfWeek;
import java.util.Objects;

public class RegularHoliday {

	private int week;
	private DayOfWeek dayOfWeek;

	public RegularHoliday(WeekWrapper weekWrapper, DayOfWeekWrapper dayOfWeekWrapper) {
		week = weekWrapper.getWeek();
		dayOfWeek = dayOfWeekWrapper.getDayOfWeek();
	}

	public static RegularHoliday of(WeekWrapper weekWrapper, DayOfWeekWrapper dayOfWeekWrapper) {
		return new RegularHoliday(weekWrapper, dayOfWeekWrapper);
	}

	public int getWeek() {
		return week;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof RegularHoliday)) {
			return false;
		}
		RegularHoliday that = (RegularHoliday) o;
		return week == that.week &&
				dayOfWeek == that.dayOfWeek;
	}

	@Override
	public int hashCode() {
		return Objects.hash(week, dayOfWeek);
	}
}
