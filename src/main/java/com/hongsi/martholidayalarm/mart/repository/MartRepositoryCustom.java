package com.hongsi.martholidayalarm.mart.repository;

import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.util.List;

public interface MartRepositoryCustom {

	List<String> findRegionByMartType(MartType martType);

}
