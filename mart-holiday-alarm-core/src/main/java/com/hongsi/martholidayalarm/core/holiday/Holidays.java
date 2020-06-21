package com.hongsi.martholidayalarm.core.holiday;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class Holidays {

	private static final Holidays EMPTY_HOLIDAYS = new Holidays();

	private Set<Holiday> holidays = new TreeSet<>();

	private Holidays(Collection<Holiday> holidays) {
		this.holidays.addAll(holidays);
	}

	public static Holidays of(Collection<Holiday> holidays) {
		if (Objects.isNull(holidays)) {
			return EMPTY_HOLIDAYS;
		}
		return new Holidays(holidays);
	}

	public List<Holiday> getUpcomingHolidays() {
		return holidays.stream()
				.filter(Holiday::isUpcoming)
				.collect(Collectors.toList());
	}
}
