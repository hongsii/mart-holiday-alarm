package com.hongsi.martholidayalarm.mart.repository;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.PushMart;
import java.util.List;

public interface MartRepositoryCustom {

	List<MartType> findMartTypesByGrouping();

	List<String> findRegionsByMartType(MartType martType);

	List<String> findBranchNamesByMartTypeAndRegion(MartType martType, String region);

	List<PushMart> findPushMartsByHoliday(Holiday holiday);
}
