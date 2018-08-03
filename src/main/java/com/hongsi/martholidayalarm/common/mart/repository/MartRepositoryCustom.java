package com.hongsi.martholidayalarm.common.mart.repository;

import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.util.List;

public interface MartRepositoryCustom {

	List<MartType> findMartType();

	List<String> findRegionByMartType(MartType martType);

	List<String> findBranchByMartTypeAndRegion(MartType martType, String region);

}