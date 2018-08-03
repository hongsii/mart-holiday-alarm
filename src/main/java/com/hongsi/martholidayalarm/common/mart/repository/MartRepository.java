package com.hongsi.martholidayalarm.common.mart.repository;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MartRepository extends JpaRepository<Mart, Long>, MartRepositoryCustom {

	Mart findByRealId(String realId);

	Mart findByBranchName(String branchName);

	Mart findByMartTypeAndBranchName(MartType martType, String branchName);

}
