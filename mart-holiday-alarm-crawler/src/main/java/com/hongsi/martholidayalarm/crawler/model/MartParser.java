package com.hongsi.martholidayalarm.crawler.model;

import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.location.Location;
import com.hongsi.martholidayalarm.core.mart.MartType;
import com.hongsi.martholidayalarm.crawler.model.holiday.RegularHolidayGenerator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public interface MartParser {

    MartType getMartType();

    String getRealId();

    String getBranchName();

    String getRegion();

    String getPhoneNumber();

    String getAddress();

    String getOpeningHours();

    String getUrl();

    Location getLocation();

    String getHolidayText();

    List<Holiday> getHolidays();

    default List<Holiday> generateRegularHolidays(String holidayText) {
        try {
            RegularHolidayGenerator generator = RegularHolidayGenerator.from(holidayText);
            return generator.generate(LocalDate.now());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
