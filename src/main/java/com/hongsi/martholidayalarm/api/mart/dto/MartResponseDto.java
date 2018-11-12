package com.hongsi.martholidayalarm.api.mart.dto;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MartResponseDto {

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.KOREAN);

	private Long id;
	private String createdDate;
	private String modifiedDate;
	@NotBlank private String martType;
	private String branchName;
	private String region;
	private String phoneNumber;
	private String address;
	private String openingHours;
	private String url;
	private List<String> holidays;

	public MartResponseDto(Mart entity) {
		id = entity.getId();
		createdDate = formatLocalDateTime(entity.getCreatedDate());
		modifiedDate = formatLocalDateTime(entity.getModifiedDate());
		martType = entity.getMartType().getName();
		branchName = entity.getBranchName();
		region = entity.getRegion();
		phoneNumber = entity.getPhoneNumber();
		address = entity.getAddress();
		openingHours = entity.getOpeningHours();
		url = entity.getUrl();
		holidays = toHolidayDto(entity.getHolidays());
	}

	@Builder
	public MartResponseDto(Long id, String createdDate, String modifiedDate,
			@NotBlank String martType, String branchName, String region, String phoneNumber,
			String address, String openingHours, String url, List<String> holidays) {
		this.id = id;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.martType = martType;
		this.branchName = branchName;
		this.region = region;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.openingHours = openingHours;
		this.url = url;
		this.holidays = holidays;
	}

	private String formatLocalDateTime(LocalDateTime localDateTime) {
		if(localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		return localDateTime.format(DATE_TIME_FORMATTER);
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
