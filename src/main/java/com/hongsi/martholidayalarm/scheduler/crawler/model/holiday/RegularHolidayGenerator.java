package com.hongsi.martholidayalarm.scheduler.crawler.model.holiday;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

@Getter
public class RegularHolidayGenerator {

	private List<RegularHoliday> regularHolidays;

	private RegularHolidayGenerator(List<KoreanWeek> weeks, List<KoreanDayOfWeek> dayOfWeeks) {
		Objects.requireNonNull(weeks);
		Objects.requireNonNull(dayOfWeeks);
		if (weeks.isEmpty() || dayOfWeeks.isEmpty()) {
			throw new IllegalArgumentException("잘못된 파라미터입니다." + weeks + ", " + dayOfWeeks);
		}

		regularHolidays = weeks.stream()
				.flatMap(weekWrapper -> dayOfWeeks.stream()
						.map(dayOfWrapper -> new RegularHoliday(weekWrapper, dayOfWrapper)))
				.collect(Collectors.toList());
	}

	public static RegularHolidayGenerator parse(String holidayText) {
		List<KoreanWeek> weeks = KoreanWeek.parseToCollection(holidayText);
		List<KoreanDayOfWeek> dayOfWeeks = KoreanDayOfWeek.parseToCollection(holidayText);
		return new RegularHolidayGenerator(weeks, dayOfWeeks);
	}

	public List<Holiday> generate() {
		return generate(LocalDate.now());
	}

	public List<Holiday> generate(LocalDate startDate) {
		Stream<Holiday> stream = Stream.empty();
		for (RegularHolidayParser regularHolidayParser = new RegularHolidayParser(startDate);
				regularHolidayParser.canParse(); regularHolidayParser.plusMonth()) {
			Stream<Holiday> holidayStream = generateHoliday(regularHolidayParser);
			stream = Stream.concat(stream, holidayStream);
		}
		return stream.sorted()
				.collect(Collectors.toList());
	}

	private Stream<Holiday> generateHoliday(RegularHolidayParser regularHolidayParser) {
		Builder<LocalDate> builder = Stream.builder();
		for (RegularHoliday regularHoliday : regularHolidays) {
			LocalDate date = regularHolidayParser.generateNthDayOfWeekInMonth(regularHoliday);
			builder.add(date);
		}
		return builder.build()
				.filter(regularHolidayParser::isInRange)
				.map(Holiday::of);
	}
}
