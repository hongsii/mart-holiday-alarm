package com.hongsi.martholidayalarm.api.dto.mart;

import com.hongsi.martholidayalarm.core.mart.Mart;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MartDto {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.KOREAN);
    private static final DateTimeFormatter HOLIDAY_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd (EE)").withLocale(Locale.KOREAN);

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
    private LocationDto location;

    public MartDto(Mart mart) {
        this.id = mart.getId();
        this.createdDate = formatDate(mart.getCreatedDate());
        this.modifiedDate = formatDate(mart.getModifiedDate());
        this.martType = mart.getMartType().getName();
        this.branchName = mart.getBranchName();
        this.region = mart.getRegion();
        this.phoneNumber = mart.getPhoneNumber();
        this.address = mart.getAddress();
        this.openingHours = mart.getOpeningHours();
        this.url = mart.getUrl();
        this.holidays = formatHolidays(mart);
        this.location = new LocationDto(mart.getLocation(), null);
    }

    public MartDto(Mart mart, Double distance) {
        this.id = mart.getId();
        this.createdDate = formatDate(mart.getCreatedDate());
        this.modifiedDate = formatDate(mart.getModifiedDate());
        this.martType = mart.getMartType().getName();
        this.branchName = mart.getBranchName();
        this.region = mart.getRegion();
        this.phoneNumber = mart.getPhoneNumber();
        this.address = mart.getAddress();
        this.openingHours = mart.getOpeningHours();
        this.url = mart.getUrl();
        this.holidays = formatHolidays(mart);
        this.location = new LocationDto(mart.getLocation(), distance);
    }

    private List<String> formatHolidays(Mart mart) {
        return mart.getUpcomingHolidays()
                .stream()
                .map(holiday -> holiday.getFormattedHoliday(HOLIDAY_FORMATTER))
                .collect(Collectors.toList());
    }

    private String formatDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now();
        }
        return localDateTime.format(DATE_TIME_FORMATTER);
    }
}
