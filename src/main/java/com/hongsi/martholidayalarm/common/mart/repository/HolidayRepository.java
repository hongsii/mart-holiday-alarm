package com.hongsi.martholidayalarm.common.mart.repository;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

}
