package com.hongsi.martholidayalarm.mart.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class HolidayId implements Serializable {

	@Column(name = "date")
	private LocalDate date;

	private Long martId;

	@Builder
	public HolidayId(LocalDate date, Long martId) {
		this.date = date;
		this.martId = martId;
	}

	@Override
	public String toString() {
		return "HolidayId{" +
				"date=" + date +
				", martId=" + martId +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof HolidayId)) {
			return false;
		}

		HolidayId holidayId = (HolidayId) o;
		if (!date.equals(holidayId.date)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = date.hashCode();
		result = 31 * result + (martId != null ? martId.hashCode() : 0);
		return result;
	}
}
