package com.hongsi.martholidayalarm.mart.repository;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.PushMart;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface MartRepositoryCustom {

	List<Mart> findMarts(Sort sort);

	List<Mart> findMartsById(Collection<Long> ids, Sort sort);

	List<Mart> findMartsByMartType(MartType martType, Sort sort);
	List<MartType> findMartTypesByGrouping();

	List<String> findRegionsByMartType(MartType martType);
	List<String> findBranchNamesByMartTypeAndRegion(MartType martType, String region);
	List<PushMart> findPushMartsByHoliday(Holiday holiday);
}
