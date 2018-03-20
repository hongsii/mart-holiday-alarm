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
	public int hashCode() {
		return date.hashCode() + martId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		try {
			return date.equals(((HolidayId) obj).date) &&
					martId.equals(((HolidayId) obj).martId);
		} catch (Exception ex) {
			return false;
		}
	}
}
