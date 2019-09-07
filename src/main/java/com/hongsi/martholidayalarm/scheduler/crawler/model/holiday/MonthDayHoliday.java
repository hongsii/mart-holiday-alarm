package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import lombok.extern.slf4j.Slf4j;

import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import static java.util.Arrays.asList;

@Slf4j
public class MonthDayHoliday {

	private static final List<Month> NEXT_YEAR_CONDITION = asList(Month.JANUARY, Month.FEBRUARY);

	private Month month;
	private int dayOfMonth;

	private MonthDayHoliday(MonthDay monthDay) {
		month = monthDay.getMonth();
		dayOfMonth = monthDay.getDayOfMonth();
	}

	public static MonthDayHoliday of(String holidayText, DateTimeFormatter dateTimeFormatter) {
		try {
			return of(MonthDay.parse(holidayText, dateTimeFormatter));
		} catch (NullPointerException | DateTimeParseException e) {
			log.error("[ERROR][CRAWLING] {}, {}, {}", holidayText, dateTimeFormatter,
					e.getMessage());
			throw new IllegalArgumentException("날짜를 파싱할 수 없습니다.");
		}
	}

	public static MonthDayHoliday of(MonthDay monthDay) {
		return new MonthDayHoliday(monthDay);
	}

	public Holiday toHoliday() {
		return Holiday.of(
				getYearFromMonth(YearMonth.now()),
				month.getValue(),
				dayOfMonth);
	}

	/**
	 * 마트 휴무일은 1-2개월마다 업데이트되기 때문에 11-12월에 휴무일을 수집할 경우 휴무일이 1-2월이면 내년연도로 설정
	 */
	public int getYearFromMonth(YearMonth currentYearMonth) {
		Month currentMonth = currentYearMonth.getMonth();
		if (currentMonth.getValue() < Month.NOVEMBER.getValue()) {
			return currentYearMonth.getYear();
		}

		return (isMonthOfNextYear()) ? currentYearMonth.getYear() + 1 : currentYearMonth.getYear();
	}

	private boolean isMonthOfNextYear() {
		return NEXT_YEAR_CONDITION.contains(this.month);
	}

	@Override
	public String toString() {
		return "MonthDayHoliday{" +
				"month=" + month +
				", dayOfMonth=" + dayOfMonth +
				'}';
	}
}
