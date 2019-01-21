package com.hongsi.martholidayalarm.service.dto.mart;

import com.hongsi.martholidayalarm.domain.mart.Mart;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MartDto {

	@Getter
	@Setter
	public static class Response {

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
		private LocationDto.Response location;

		@Builder
		private Response(Long id, String createdDate, String modifiedDate,
				String martType, String branchName, String region, String phoneNumber,
				String address, String openingHours, String url, List<String> holidays,
				LocationDto.Response location) {
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
			this.location = location;
		}

		public static Response of(Mart entity) {
			return Response.builder()
					.id(entity.getId())
					.createdDate(formatDate(entity.getCreatedDate()))
					.modifiedDate(formatDate(entity.getModifiedDate()))
					.martType(entity.getMartType().getDisplayName())
					.branchName(entity.getBranchName())
					.region(entity.getRegion())
					.phoneNumber(entity.getPhoneNumber())
					.address(entity.getAddress())
					.openingHours(entity.getOpeningHours())
					.url(entity.getUrl())
					.holidays(entity.getHolidays().getFormattedHolidays())
					.location(LocationDto.Response.of(entity.getLocation()))
					.build();
		}

		private static String formatDate(LocalDateTime localDateTime) {
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
}
