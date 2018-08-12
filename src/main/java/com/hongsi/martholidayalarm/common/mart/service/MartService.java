package com.hongsi.martholidayalarm.common.mart.service;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.common.mart.dto.MartDto;
import com.hongsi.martholidayalarm.common.mart.repository.HolidayRepository;
import com.hongsi.martholidayalarm.common.mart.repository.MartRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MartService {

	private final MartRepository martRepository;

	private final HolidayRepository holidayRepository;

	public Mart getMart(MartType martType, String branchName) {
		return martRepository.findByMartTypeAndBranchName(martType, branchName);
	}

	public List<MartType> getMartTypes() {
		return martRepository.findMartType();
	}

	public List<String> getRegions(MartType martType) {
		return martRepository.findRegionByMartType(martType);
	}

	public List<String> getBranches(MartType martType, String region) {
		return martRepository.findBranchByMartTypeAndRegion(martType, region);
	}

	public List<MartDto> getMartsByMartType(String martTypeStr) throws IllegalArgumentException {
		MartType martType = MartType.typeOf(martTypeStr);
		return martRepository.findByMartType(martType)
				.stream()
				.map(MartDto::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public void saveAll(List<Mart> marts) {
		List<Mart> savedMarts = martRepository.findAll();
		for (Mart mart : marts) {
			if (savedMarts.contains(mart)) {
				int index = savedMarts.indexOf(mart);
				mart.update(savedMarts.get(index));
			}
		}
		martRepository.saveAll(marts);
	}


	@Transactional
	public int removeNotUpdatedMart(int days) {
		LocalDateTime conditionTime = martRepository.findMaxModifiedDate();
		log.info("Last modified date of Mart : {}, minus days : {}", conditionTime, days);
		conditionTime = conditionTime.minusDays(days);
		log.info("Condition time for finding not updated Mart : {}", conditionTime);
		List<Mart> notUpdatedMarts = martRepository
				.findByModifiedDateLessThanOrEqual(conditionTime);
		if (notUpdatedMarts.isEmpty()) {
			log.info("Not exists Mart to remove");
			return 0;
		}

		List<Long> ids = new ArrayList<>();
		for (Mart mart : notUpdatedMarts) {
			log.info(" > Mart info to remove [Id : {}, CreatedDate : {}, ModifiedDate : {}"
							+ ", MartType : {}, Region : {}, BranchName : {}]",
					mart.getId(), mart.getCreatedDate(), mart.getModifiedDate(), mart.getMartType(),
					mart.getRegion(), mart.getBranchName());
			ids.add(mart.getId());
		}
		holidayRepository.deleteByMartIds(ids);
		martRepository.deleteByIds(ids);
		return ids.size();
	}
}
