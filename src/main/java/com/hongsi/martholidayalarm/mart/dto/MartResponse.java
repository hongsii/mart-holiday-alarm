package com.hongsi.martholidayalarm.mart.dto;

import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MartResponse {

	public static final String EMPTY = "";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.KOREAN);
	private Long id;
	private String createdDate;
	private String modifiedDate;
	private String martType;
	private String branchName;
	private String region;
	private String phoneNumber;
	private String address;
	private String openingHours;
	private String url;
	private List<String> holidays;

	public MartResponse(Mart entity) {
		id = entity.getId();
		createdDate = formatDate(Optional.ofNullable(entity.getCreatedDate())
				.orElse(LocalDateTime.now()));
		modifiedDate = formatDate(Optional.ofNullable(entity.getModifiedDate())
				.orElse(LocalDateTime.now()));
		martType = entity.getMartType().getDisplayName();
		branchName = Optional.ofNullable(entity.getBranchName())
				.orElse(EMPTY);
		region = Optional.ofNullable(entity.getRegion()).orElse(EMPTY);
		phoneNumber = Optional.ofNullable(entity.getPhoneNumber())
				.orElse(EMPTY);
		address = Optional.ofNullable(entity.getAddress())
				.orElse(EMPTY);
		openingHours = Optional.ofNullable(entity.getOpeningHours())
				.orElse(EMPTY);
		url = Optional.ofNullable(entity.getUrl())
				.orElse(EMPTY);
		holidays = entity.getHolidays().getFormattedHolidays();
	}

	@Builder
	public MartResponse(Long id, String createdDate, String modifiedDate,
			@NotBlank MartType martType, String branchName, String region, String phoneNumber,
			String address, String openingHours, String url, List<String> holidays) {
		this.id = id;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.martType = martType.getDisplayName();
		this.branchName = branchName;
		this.region = region;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.openingHours = openingHours;
		this.url = url;
		this.holidays = holidays;
	}

	private String formatDate(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		return localDateTime.format(DATE_TIME_FORMATTER);
	}

	public DeprecatedMartResponse toTempResponse() {
		return DeprecatedMartResponse.builder()
				.address(address)
				.branchName(branchName)
				.createdDate(createdDate)
				.modifiedDate(modifiedDate)
				.id(id)
				.martType(martType)
				.openingHours(openingHours)
				.phoneNumber(phoneNumber)
				.region(region)
				.url(url)
				.holidays(holidays)
				.build();
	}
}
