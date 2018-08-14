package com.hongsi.martholidayalarm.common.mart.dto;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@ApiModel(value = "마트 정보")
public class MartDto {

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH24:MI:SS").withLocale(Locale.KOREAN);

	@ApiModelProperty(value = "고유 아이디", dataType = "java.lang.Long", required = true, position = 1)
	private Long id;

	@ApiModelProperty(value = "생성일", dataType = "java.lang.String", required = true, position = 2)
	private String createdDate;

	@ApiModelProperty(value = "마지막 수정일", dataType = "java.lang.String", required = true, position = 3)
	private String modifiedDate;

	@ApiModelProperty(value = "마트타입", dataType = "java.lang.String", required = true, position = 4, example = "마트 한글명 ex) 이마트")
	@NotBlank
	private String martType;

	@ApiModelProperty(value = "지점명", dataType = "java.lang.String", required = true, position = 5)
	private String branchName;

	@ApiModelProperty(value = "지역", dataType = "java.lang.String", required = true, position = 6)
	private String region;

	@ApiModelProperty(value = "전화번호", dataType = "java.lang.String", required = true, position = 7)
	private String phoneNumber;

	@ApiModelProperty(value = "주소", dataType = "java.lang.String", required = true, position = 8)
	private String address;

	@ApiModelProperty(value = "영업시간", dataType = "java.lang.String", position = 9, allowEmptyValue = true)
	private String openingHours;

	@ApiModelProperty(value = "마트 url", dataType = "java.lang.String", position = 10, allowEmptyValue = true)
	private String url;

	@ApiModelProperty(value = "휴무일", dataType = "java.lang.String", position = 11, allowEmptyValue = true)
	private List<String> holidays;

	public MartDto(Mart entity) {
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

	private String formatLocalDateTime(LocalDateTime localDateTime) {
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
