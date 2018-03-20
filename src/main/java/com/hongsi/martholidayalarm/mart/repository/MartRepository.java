package com.hongsi.martholidayalarm.mart.repository;

import com.hongsi.martholidayalarm.mart.domain.Mart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MartRepository extends JpaRepository<Mart, Long> {

	Mart findByRealId(String realId);
}
