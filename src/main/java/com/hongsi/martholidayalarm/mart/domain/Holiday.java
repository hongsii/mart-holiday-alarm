package com.hongsi.martholidayalarm.mart.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Holiday extends BaseEntity {

	public static final DateTimeFormatter DEFAULT_FORMATTER_WITH_DAYOFWEEK = DateTimeFormatter
			.ofPattern("yyyy-MM-dd (EE)").withLocale(Locale.KOREAN);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDate date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mart_id")
	private Mart mart;

	@Builder
	public Holiday(LocalDate date, Mart mart) {
		this.date = date;
		this.mart = mart;
	}

	public String getFormattedHolidayWithDayOfWeek() {
		return date.format(DEFAULT_FORMATTER_WITH_DAYOFWEEK);
	}

	public boolean isUpcoming() {
		return !date.isBefore(LocalDate.now());
	}

	public void addedFromMart(Mart mart) {
		this.mart = mart;
	}

	@Override
	public String toString() {
		return "Holiday{" +
				"date=" + date +
				", id=" + id +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Holiday)) {
			return false;
		}

		Holiday holiday = (Holiday) o;

		return date.isEqual(holiday.date);
	}

	@Override
	public int hashCode() {
		int result = date.hashCode();
		result = 31 * result + mart.hashCode();
		return result;
	}
}
