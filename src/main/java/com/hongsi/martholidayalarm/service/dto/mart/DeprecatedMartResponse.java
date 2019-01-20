package com.hongsi.martholidayalarm.service.dto.mart;

import com.hongsi.martholidayalarm.domain.mart.Mart;
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
@Deprecated
public class DeprecatedMartResponse {

	public static final String EMPTY = "";
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.KOREAN);
	private Long id;
	private String createdDate;
	private String modifiedDate;
	@NotBlank
	private String martType;
	private String branchName;
	private String region;
	private String phoneNumber;
	private String address;
	private String openingHours;
	private String url;
	private List<String> holidays;

	public DeprecatedMartResponse(Mart entity) {
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
	public DeprecatedMartResponse(Long id, String createdDate, String modifiedDate,
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

	private String formatDate(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			localDateTime = LocalDateTime.now();
		}
		return localDateTime.format(DATE_TIME_FORMATTER);
	}
}
