package com.hongsi.martholidayalarm.mart.dto;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class PushMart {

	private static final DateTimeFormatter PUSH_HOLIDAY_FORMATTER = DateTimeFormatter
			.ofPattern("d일").withLocale(Locale.KOREAN);

	private Long id;
	private MartType martType;
	private String branchName;
	private Holiday holiday;

	public PushMart(Long id, MartType martType, String branchName, Holiday holiday) {
		this.id = id;
		this.martType = martType;
		this.branchName = branchName;
		this.holiday = holiday;
	}

	public String getMartType() {
		return martType.getDisplayName();
	}

	public String getFormattedHoliday() {
		return holiday.getFormattedHoliday(PUSH_HOLIDAY_FORMATTER);
	}
}
