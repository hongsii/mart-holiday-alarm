package com.hongsi.martholidayalarm.repository;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.domain.push.PushMart;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface MartRepositoryCustom {

	List<Mart> findMarts(Sort sort);
	List<Mart> findMartsById(Collection<Long> ids, Sort sort);
	List<Mart> findMartsByLocation(Location location, int distance);
	List<Mart> findMartsByMartType(MartType martType, Sort sort);
	List<MartType> findMartTypesByGrouping();

	List<String> findRegionsByMartType(MartType martType);
	List<String> findBranchNamesByMartTypeAndRegion(MartType martType, String region);
	List<PushMart> findPushMartsByHoliday(Holiday holiday);
}
