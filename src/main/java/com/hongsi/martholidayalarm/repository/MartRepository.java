package com.hongsi.martholidayalarm.repository;

import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MartRepository extends JpaRepository<Mart, Long>, MartRepositoryCustom {

	Optional<Mart> findByRealIdAndMartType(String realId, MartType martType);
	Optional<Mart> findByMartTypeAndBranchName(MartType martType, String branchName);
}
