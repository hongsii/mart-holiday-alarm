package com.hongsi.martholidayalarm.domain.push;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartType;
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

	private Long id;
	private MartType martType;
	private String branchName;
	private Holiday holiday;

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
		return martType.getDisplayName();
	}

	public String getFormattedHoliday() {
		return holiday.getFormattedHoliday(PUSH_HOLIDAY_FORMATTER);
	}
}
