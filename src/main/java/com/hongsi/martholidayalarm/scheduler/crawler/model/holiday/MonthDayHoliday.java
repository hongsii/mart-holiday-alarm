package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import lombok.extern.slf4j.Slf4j;

import java.time.Month;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

@Slf4j
public class MonthDayHoliday {

	private static final List<Month> NEXT_YEAR_CONDITION = asList(Month.JANUARY, Month.FEBRUARY);

	private Month month;
	private int dayOfMonth;

	public MonthDayHoliday(Month month, int dayOfMonth) {
		this.month = month;
		this.dayOfMonth = dayOfMonth;
	}

	public static MonthDayHoliday parse(String text, DateTimeFormatter dateTimeFormatter) {
		try {
			MonthDay monthDay = MonthDay.parse(text, dateTimeFormatter);
			return new MonthDayHoliday(monthDay.getMonth(), monthDay.getDayOfMonth());
		} catch (NullPointerException | DateTimeParseException e) {
			log.error("[CRAWLING] can't parse month, day. text : {}, formatter : {}, message : {}", text, dateTimeFormatter, e.getMessage());
			throw new IllegalArgumentException("날짜를 파싱할 수 없습니다.");
		}
	}

	/**
	 * 마트 휴무일은 1-2개월마다 업데이트되므로 11, 12월에 휴무일 수집 시 1, 2월이면 내년 연도로 설정
	 */
	public int getYearFromMonth(YearMonth currentYearMonth) {
		Month currentMonth = currentYearMonth.getMonth();
		if (currentMonth.getValue() < Month.NOVEMBER.getValue()) {
			return currentYearMonth.getYear();
		}
		return (isMonthOfNextYear()) ? currentYearMonth.getYear() + 1 : currentYearMonth.getYear();
	}

	private boolean isMonthOfNextYear() {
		return NEXT_YEAR_CONDITION.contains(month);
	}

	public Holiday toHoliday() {
		return Holiday.of(getYearFromMonth(YearMonth.now()), month.getValue(), dayOfMonth);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MonthDayHoliday that = (MonthDayHoliday) o;
		return dayOfMonth == that.dayOfMonth &&
				month == that.month;
	}

	@Override
	public int hashCode() {
		return Objects.hash(month, dayOfMonth);
	}

	@Override
	public String toString() {
		return "MonthDayHoliday{" +
				"month=" + month +
				", dayOfMonth=" + dayOfMonth +
				'}';
	}
}
