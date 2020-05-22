package com.hongsi.martholidayalarm.crawler.model.emart;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.core.holiday.Holiday;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmartHolidayParser {

    private static final DateTimeFormatter HOLIDAY_FORMATTER = DateTimeFormatter
            .ofPattern("yyyyMMdd");
    private static final int VALID_DATE_LENGTH = 8;

    @JsonProperty("JIJUM_ID")
    private String realId;

    private final List<Holiday> holidays = new ArrayList<>();

    public boolean isSameId(String realId) {
        if (this.realId == null || realId == null) {
            return false;
        }
        return this.realId.equals(realId);
    }

    public String getRealId() {
        return realId;
    }

    public void setRealId(String realId) {
        this.realId = realId;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    @JsonAlias({"HOLIDAY_DAY1_YMD", "HOLIDAY_DAY2_YMD", "HOLIDAY_DAY3_YMD"})
    public void setHolidays(String rawHoliday) {
        if (rawHoliday.length() == VALID_DATE_LENGTH) {
            LocalDate date = LocalDate.parse(rawHoliday, HOLIDAY_FORMATTER);
            holidays.add(Holiday.of(date));
        }
    }
}
