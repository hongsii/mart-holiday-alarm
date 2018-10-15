package com.hongsi.martholidayalarm.common.mart.repository;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.time.LocalDateTime;
import java.util.List;

public interface MartRepositoryCustom {

	List<MartType> findMartType();

	List<String> findRegionByMartType(MartType martType);

	List<String> findBranchByMartTypeAndRegion(MartType martType, String region);

	LocalDateTime findMaxModifiedDate();

	List<Mart> findByModifiedDateLessThanOrEqual(LocalDateTime localDateTime);

	List<Mart> findHavingSameHoliday(Holiday holiday);
}
