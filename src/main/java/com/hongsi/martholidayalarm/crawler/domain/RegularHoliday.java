package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

@Getter
public class RegularHoliday {

	public static final int MAX_MONTH_COUNTING = 2;

	private final List<Integer> weeks = new ArrayList<>();
	private final List<DayOfWeek> dayOfWeeks = new ArrayList<>();
	private String regularHolidayText;

	RegularHoliday(String regularHolidayText) {
		this.regularHolidayText = regularHolidayText;
		parseWeeks();
		parseDayOfWeeks();
	}

	private void parseWeeks() {
		final Pattern weekPattern = Pattern.compile("(첫|둘|셋|넷|다섯|[1-5])(\\s+)?[째|주]");
		Matcher matcher = weekPattern.matcher(regularHolidayText);
		while (matcher.find()) {
			weeks.add(getWeekFromText(matcher.group()));
		}
	}

	private int getWeekFromText(String textOfWeek) {
		String weekText = textOfWeek.substring(0, 1);
		switch (weekText) {
			case "첫":
			case "1":
				return 1;
			case "둘":
			case "2":
				return 2;
			case "셋":
			case "3":
				return 3;
			case "넷":
			case "4":
				return 4;
			case "다섯":
			case "5":
				return 5;
		}
		throw new IllegalArgumentException("Not found week from this text : " + textOfWeek);
	}

	private void parseDayOfWeeks() {
		final Pattern dayOfPattern = Pattern.compile(".요일");
		Matcher matcher = dayOfPattern.matcher(regularHolidayText);
		while (matcher.find()) {
			dayOfWeeks.add(getDayOfWeekFromText(matcher.group()));
		}
	}

	private DayOfWeek getDayOfWeekFromText(String textOfWeek) {
		return Arrays.stream(DayOfWeek.values())
				.filter(dayOfWeek -> textOfWeek
						.startsWith(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(
						"Not found DayOfWeek from this text : " + textOfWeek));
	}

	public List<Holiday> makeRegularHolidays() {
		LocalDate startDate = LocalDate.now();
		return makeRegularHolidays(startDate);
	}

	public List<Holiday> makeRegularHolidays(LocalDate startDate) {
		List<Holiday> holidays = new ArrayList<>();
		if (weeks.isEmpty() || dayOfWeeks.isEmpty()) {
			return holidays;
		}

		LocalDate endDate = startDate.withDayOfMonth(1).plusMonths(MAX_MONTH_COUNTING);
		while (!startDate.isAfter(endDate)) {
			if (isHoliday(startDate)) {
				holidays.add(Holiday.builder().date(startDate).build());
			}
			startDate = startDate.plusDays(1);
		}
		return holidays;
	}

	private boolean isHoliday(LocalDate date) {
		return weeks.contains(date.get(ChronoField.ALIGNED_WEEK_OF_MONTH))
				&& dayOfWeeks.contains(date.getDayOfWeek());
	}
}
