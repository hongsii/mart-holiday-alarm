package com.hongsi.martholidayalarm.common.mart.repository;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MartRepository extends JpaRepository<Mart, Long>, MartRepositoryCustom {

	Mart findByRealId(String realId);

	Mart findByBranchName(String branchName);

	Mart findByMartTypeAndBranchName(MartType martType, String branchName);

	List<Mart> findByMartType(MartType martType);

	@Query(value = "SELECT max(M.modified_date) FROM Mart M")
	LocalDateTime findMaxModifiedDate();

	@Transactional
	@Modifying
	@Query("DELETE FROM Mart M WHERE M.id IN :ids")
	void deleteByIds(@Param("ids") List<Long> ids);
}
