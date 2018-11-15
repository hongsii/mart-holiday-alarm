package com.hongsi.martholidayalarm.api.mart.dto;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
		try {
			id = entity.getId();
			createdDate = formatLocalDateTime(
					Optional.ofNullable(entity.getCreatedDate()).orElse(LocalDateTime.now()));
			modifiedDate = formatLocalDateTime(
					Optional.ofNullable(entity.getModifiedDate()).orElse(LocalDateTime.now()));
			martType = Optional.ofNullable(entity.getMartType()).orElse(MartType.EMART).getName();
			branchName = Optional.ofNullable(entity.getBranchName()).orElse("");
			region = Optional.ofNullable(entity.getRegion()).orElse("");
			phoneNumber = Optional.ofNullable(entity.getPhoneNumber()).orElse("");
			address = Optional.ofNullable(entity.getAddress()).orElse("");
			openingHours = Optional.ofNullable(entity.getOpeningHours()).orElse("");
			url = Optional.ofNullable(entity.getUrl()).orElse("");
			holidays = toHolidayDto(entity.getHolidays());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
