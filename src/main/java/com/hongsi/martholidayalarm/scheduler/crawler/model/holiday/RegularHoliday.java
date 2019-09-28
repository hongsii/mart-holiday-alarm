package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import java.time.DayOfWeek;
import java.util.Objects;

public class RegularHoliday {

	private int week;
	private DayOfWeek dayOfWeek;

	private RegularHoliday(KoreanWeek koreanWeek, KoreanDayOfWeek koreanDayOfWeek) {
		week = koreanWeek.getWeek();
		dayOfWeek = koreanDayOfWeek.getDayOfWeek();
	}

	public static RegularHoliday of(KoreanWeek koreanWeek, KoreanDayOfWeek koreanDayOfWeek) {
		return new RegularHoliday(koreanWeek, koreanDayOfWeek);
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
