package com.hongsi.martholidayalarm.push.repository;

import com.hongsi.martholidayalarm.push.model.PushMart;

import java.time.LocalDate;
import java.util.List;

public interface PushMartRepositoryCustom {

    List<PushMart> findAllByIdInAndHolidayDate(List<Long> ids, LocalDate holidayDate);
}
