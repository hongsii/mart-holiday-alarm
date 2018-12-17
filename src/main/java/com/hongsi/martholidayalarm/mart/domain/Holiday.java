package com.hongsi.martholidayalarm.mart.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Holiday implements Comparable<Holiday> {

	private static final DateTimeFormatter VIEW_DATE_FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-M-d (EE)").withLocale(Locale.KOREAN);

	@Column
	private LocalDate date;

	public Holiday(LocalDate date) {
		this.date = date;
	}

	public static Holiday of(int year, int month, int dayOfMonth) {
		return of(LocalDate.of(year, month, dayOfMonth));
	}

	public static Holiday of(String holidayText) {
		return of(LocalDate.parse(holidayText));
	}

	public static Holiday of(LocalDate date) {
		return new Holiday(date);
	}

	public boolean isUpcoming() {
		return !date.isBefore(LocalDate.now());
	}

	public String getFormattedHoliday() {
		return getFormattedHoliday(VIEW_DATE_FORMATTER);
	}

	public String getFormattedHoliday(DateTimeFormatter formatter) {
		return date.format(formatter);
	}

	@Override
	public int compareTo(Holiday other) {
		return date.compareTo(other.date);
	}
}
