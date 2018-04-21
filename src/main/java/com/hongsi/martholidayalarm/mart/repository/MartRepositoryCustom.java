package com.hongsi.martholidayalarm.mart.repository;

import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.util.List;

public interface MartRepositoryCustom {

	List<MartType> findMartType();

	List<String> findRegionByMartType(MartType martType);

	List<String> findBranchByMartTypeAndRegion(MartType martType, String region);

}
