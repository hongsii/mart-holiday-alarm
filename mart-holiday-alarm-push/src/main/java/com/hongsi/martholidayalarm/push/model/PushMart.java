package com.hongsi.martholidayalarm.push.model;

import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.mart.Mart;
import com.hongsi.martholidayalarm.core.mart.MartType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class PushMart {

	private static final DateTimeFormatter PUSH_HOLIDAY_FORMATTER = DateTimeFormatter
			.ofPattern("d일").withLocale(Locale.KOREAN);

	private final Long id;
	private final MartType martType;
	private final String branchName;
	private final Holiday holiday;

	@Builder
	public PushMart(Long id, MartType martType, String branchName, Holiday holiday) {
		this.id = id;
		this.martType = martType;
		this.branchName = branchName;
		this.holiday = holiday;
	}

	public static PushMart from(Mart mart) {
		return new PushMart(mart.getId(), mart.getMartType(), mart.getBranchName(), mart.getUpcomingHoliday());
	}

	public String getMartType() {
		return martType.getName();
	}

	public String getFormattedHoliday() {
		return holiday.getFormattedHoliday(PUSH_HOLIDAY_FORMATTER);
	}
}
