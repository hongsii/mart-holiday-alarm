package com.hongsi.martholidayalarm.mart.dto;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Holidays {

	private Set<Holiday> holidays = new TreeSet<>();

	public Holidays(Collection<Holiday> holidays) {
		this.holidays.addAll(holidays);
	}

	public List<Holiday> getUpcomingHolidays() {
		return holidays.stream()
				.filter(Holiday::isUpcoming)
				.collect(Collectors.toList());
	}

	public List<String> getFormattedHolidays() {
		return holidays.stream()
				.map(Holiday::getFormattedHoliday)
				.collect(Collectors.toList());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Holidays)) {
			return false;
		}
		Holidays holidays1 = (Holidays) o;
		return Objects.equals(holidays, holidays1.holidays);
	}

	@Override
	public int hashCode() {
		return Objects.hash(holidays);
	}
}
