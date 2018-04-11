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

	@Column
	LocalDate date;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mart_id")
	private Mart mart;

	@Builder
	public Holiday(LocalDate date, Mart mart) {
		this.date = date;
		this.mart = mart;
		this.mart.addHoliday(this);
	}

	public LocalDate getHoliday() {
		return this.date;
	}

	public void readyForUpdate(Holiday holiday) {
		this.id = holiday.getId();
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
