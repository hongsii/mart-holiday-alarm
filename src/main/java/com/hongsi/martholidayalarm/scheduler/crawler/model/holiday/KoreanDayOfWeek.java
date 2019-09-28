package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import com.hongsi.martholidayalarm.utils.MatchSpliterator;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum KoreanDayOfWeek {

	MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

	private static final Pattern DAY_OF_WEEK_PATTERN = Pattern.compile(".요일");

	private String displayName;

	KoreanDayOfWeek() {
		displayName = DayOfWeek.valueOf(name()).getDisplayName(TextStyle.FULL, Locale.KOREA);
	}

	public static List<KoreanDayOfWeek> parseToCollection(String text) {
		Matcher matcher = DAY_OF_WEEK_PATTERN.matcher(text);
		return MatchSpliterator.from(matcher).stream()
				.map(KoreanDayOfWeek::of)
				.collect(Collectors.toList());
	}

	public static KoreanDayOfWeek of(String text) {
		return Arrays.stream(values())
				.filter(dayOfWeek -> dayOfWeek.startsWith(text))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"Not found DayOfWeek of this text : " + text));
	}

	public boolean startsWith(String text) {
		return displayName.startsWith(text);
	}

	public DayOfWeek getDayOfWeek() {
		return DayOfWeek.valueOf(name());
	}
}
