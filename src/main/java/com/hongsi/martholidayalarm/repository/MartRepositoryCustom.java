package com.hongsi.martholidayalarm.repository;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;

public interface MartRepositoryCustom {

	List<Mart> findAllWithHoliday(Sort sort);
	List<Mart> findAllWithHolidayById(Collection<Long> ids, Sort sort);
	List<Mart> findAllByLocation(Location location, int distance);
	List<Mart> findAllByMartType(MartType martType, Sort sort);
	List<Mart> findAllByHolidayInnerJoinHoliday(Holiday holiday);

	List<MartType> findMartTypes();
	List<String> findRegionsByMartType(MartType martType);
	List<String> findBranchNamesByMartTypeAndRegion(MartType martType, String region);
}
