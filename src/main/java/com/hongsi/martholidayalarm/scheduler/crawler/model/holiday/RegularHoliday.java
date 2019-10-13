package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

public class RegularHoliday {

	private static final int FIRST_DAY_IN_MONTH = 1;

	private int week;
	private DayOfWeek dayOfWeek;

	private RegularHoliday(KoreanWeek koreanWeek, KoreanDayOfWeek koreanDayOfWeek) {
		week = koreanWeek.getWeek();
		dayOfWeek = koreanDayOfWeek.getDayOfWeek();
	}

	public static RegularHoliday of(KoreanWeek koreanWeek, KoreanDayOfWeek koreanDayOfWeek) {
		if (koreanWeek == null || koreanDayOfWeek == null) {
			throw new IllegalArgumentException("Can't create RegularHoliday");
		}
		return new RegularHoliday(koreanWeek, koreanDayOfWeek);
	}

	public LocalDate parse(YearMonth yearMonth) {
		return yearMonth.atDay(FIRST_DAY_IN_MONTH)
				.with(TemporalAdjusters.dayOfWeekInMonth(week, dayOfWeek));
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
