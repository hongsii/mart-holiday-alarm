package com.hongsi.martholidayalarm.domain.crawler.holiday;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

public class RegularHolidayParser {

	private static final int MAX_ADDITION_MONTH = 1;
	private static final int FIRST_DAY_IN_MONTH = 1;

	private LocalDate startDate;
	private YearMonth current;
	private YearMonth end;

	public RegularHolidayParser(LocalDate startDate) {
		this.startDate = startDate;
		current = YearMonth.from(startDate);
		end = current.plusMonths(MAX_ADDITION_MONTH);
	}

	public boolean canParse() {
		return current.isBefore(end) || current.equals(end);
	}

	public LocalDate generateNthDayOfWeekInMonth(RegularHoliday regularHoliday) {
		return current.atDay(FIRST_DAY_IN_MONTH)
				.with(temporal -> temporal
						.with(TemporalAdjusters
								.dayOfWeekInMonth(regularHoliday.getWeek(),
										regularHoliday.getDayOfWeek())));
	}

	public YearMonth plusMonth() {
		if (!canParse()) {
			return current;
		}
		current = current.plusMonths(1);
		return current;
	}

	public boolean isInRange(LocalDate date) {
		if (date.isBefore(startDate)) {
			return false;
		}
		YearMonth yearMonthOfDate = YearMonth.from(date);
		return yearMonthOfDate.isBefore(end) || yearMonthOfDate.equals(end);
	}

	public YearMonth getCurrent() {
		return current;
	}
}
