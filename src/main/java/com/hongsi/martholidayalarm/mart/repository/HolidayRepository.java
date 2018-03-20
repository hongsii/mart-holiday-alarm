package com.hongsi.martholidayalarm.mart.repository;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.HolidayId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, HolidayId> {

}
