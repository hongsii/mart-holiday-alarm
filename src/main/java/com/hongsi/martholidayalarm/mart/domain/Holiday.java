package com.hongsi.martholidayalarm.mart.domain;

import java.time.LocalDate;
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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Holiday extends BaseEntity {

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

	public LocalDate getHoliday() {
		return date;
	}

	public void addedFromMart(Mart mart) {
		this.mart = mart;
	}

	public void readyForUpdate(Holiday holiday) {
		id = holiday.getId();
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

		if (!date.isEqual(holiday.date)) {
			return false;
		}
		return mart.equals(holiday.mart);
	}

	@Override
	public int hashCode() {
		int result = date.hashCode();
		result = 31 * result + mart.hashCode();
		return result;
	}
}
