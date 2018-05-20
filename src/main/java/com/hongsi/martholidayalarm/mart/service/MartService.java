package com.hongsi.martholidayalarm.mart.service;

import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.repository.MartRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Transactional
@Service
public class MartService {

	private MartRepository martRepository;

	public Mart createMart(MartType martType, String realId) {
		Mart savedMart = martRepository.findByRealId(realId);
		if (savedMart == null) {
			return Mart.builder()
					.martType(martType)
					.realId(realId)
					.build();
		}
		return savedMart;
	}

	public void saveAll(List<Mart> marts) {
		List<Mart> savedMarts = martRepository.findAll();
		for (Mart mart : marts) {
			if (savedMarts.contains(mart)) {
				int index = savedMarts.indexOf(mart);
				mart.readyForUpdate(savedMarts.get(index));
			}
		}
		martRepository.saveAll(marts);
	}

	public Mart getMart(String branchName) {
		return martRepository.findByBranchName(branchName);
	}

	public List<MartType> getMartTypes() {
		List<MartType> martTypes = martRepository.findMartType();
		return martTypes.stream().collect(Collectors.toList());
	}

	public List<String> getRegions(MartType martType) {
		return martRepository.findRegionByMartType(martType);
	}

	public List<String> getBranches(MartType martType, String region) {
		return martRepository.findBranchByMartTypeAndRegion(martType, region);
	}
}
