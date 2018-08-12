package com.hongsi.martholidayalarm.common.mart.dto;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MartDto {

	private Long id;

	@NotBlank
	private String martType;

	private String branchName;

	private String region;

	private String phoneNumber;

	private String address;

	private String openingHours;

	private String url;

	private List<String> holidays;

	public MartDto(Mart entity) {
		id = entity.getId();
		martType = entity.getMartType().getName();
		branchName = entity.getBranchName();
		region = entity.getRegion();
		phoneNumber = entity.getPhoneNumber();
		address = entity.getAddress();
		openingHours = entity.getOpeningHours();
		url = entity.getUrl();
		holidays = toHolidayDto(entity.getHolidays());
	}

	private List<String> toHolidayDto(List<Holiday> holidays) {
		List<String> dates = new ArrayList<>();
		for (Holiday holiday : holidays) {
			if (holiday.isUpcoming()) {
				dates.add(holiday.getFormattedHolidayWithDayOfWeek());
			}
		}
		return dates;
	}
}
