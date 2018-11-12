package com.hongsi.martholidayalarm.api.mart.repository;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MartApiRepository extends JpaRepository<Mart, Long> {

	List<Mart> findByMartType(MartType martType);
}
