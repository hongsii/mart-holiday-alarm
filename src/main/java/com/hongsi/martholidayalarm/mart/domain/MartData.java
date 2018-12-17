package com.hongsi.martholidayalarm.mart.domain;

import java.util.List;

public interface MartData {

	MartType getMartType();

	String getRealId();

	String getBranchName();

	String getRegion();

	String getPhoneNumber();

	String getAddress();

	String getOpeningHours();

	String getUrl();

	List<Holiday> getHolidays();
}
