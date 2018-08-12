package com.hongsi.martholidayalarm.common.mart.service;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.common.mart.dto.MartDTO;
import com.hongsi.martholidayalarm.common.mart.repository.HolidayRepository;
import com.hongsi.martholidayalarm.common.mart.repository.MartRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MartService {

	private final MartRepository martRepository;

	private final HolidayRepository holidayRepository;

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

	public List<MartDTO> findMartsByMartType(String martTypeStr) throws IllegalArgumentException {
		MartType martType = MartType.typeOf(martTypeStr);
		return martRepository.findByMartType(martType)
				.stream()
				.map(MartDTO::new)
				.collect(Collectors.toList());
	}

	public void removeNotUpdatedMart(int days) {
		LocalDateTime conditionTime = martRepository.findMaxModifiedDate().minusDays(days);
		List<Long> notUpdatedMartIds = martRepository.findIdByModifiedDateLessThan(conditionTime);
		holidayRepository.deleteByMartIds(notUpdatedMartIds);
		martRepository.deleteByIds(notUpdatedMartIds);
	}
}
