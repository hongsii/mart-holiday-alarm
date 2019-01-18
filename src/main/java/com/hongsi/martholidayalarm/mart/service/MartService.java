package com.hongsi.martholidayalarm.mart.service;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import com.hongsi.martholidayalarm.api.exception.ResourceNotFoundException;
import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartData;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.dto.MartResponse;
import com.hongsi.martholidayalarm.mart.dto.MartTypeResponse;
import com.hongsi.martholidayalarm.mart.dto.PushMart;
import com.hongsi.martholidayalarm.mart.repository.MartRepository;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MartService {

	private final MartRepository martRepository;

	public List<MartResponse> findMarts(Sort sort) {
		return toResponses(martRepository.findMarts(sort));
	}

	public List<MartResponse> findMartsById(Collection<Long> ids, Sort sort) {
		return toResponses(martRepository.findMartsById(ids, sort));
	}

	public MartResponse findMartById(Long id) {
		Mart mart = martRepository.findById(id)
				.orElseThrow(ResourceNotFoundException::new);
		return new MartResponse(mart);
	}

	public List<MartTypeResponse> findMartTypes() {
		return martRepository.findMartTypesByGrouping()
				.stream()
				.map(MartTypeResponse::from)
				.collect(toList());
	}

	public List<MartResponse> findMartsByMartType(MartType martType, Sort sort) {
		return toResponses(martRepository.findMartsByMartType(martType, sort));
	}

	public List<PushMart> findPushMartsByHoliday(Holiday holiday) {
		return martRepository.findPushMartsByHoliday(holiday);
	}

	public MartResponse findMartByMartTypeAndBranchName(MartType martType, String branchName) {
		return martRepository.findByMartTypeAndBranchName(martType, branchName)
				.map(MartResponse::new)
				.orElseThrow(EntityNotFoundException::new);
	}

	public List<String> findRegionsByMartType(MartType martType) {
		return martRepository.findRegionsByMartType(martType);
	}

	public List<String> findBranchNamesByMartTypeAndRegion(MartType martType, String region) {
		return martRepository.findBranchNamesByMartTypeAndRegion(martType, region);
	}

	@Transactional
	public List<Mart> saveMartData(List<? extends MartData> martData) {
		List<Mart> marts = martData.stream()
				.map(this::toEntity)
				.collect(toList());
		return saveMarts(marts);
	}

	@Transactional
	public List<Mart> saveMarts(List<Mart> marts) {
		return marts.stream()
				.map(this::save)
				.collect(toList());
	}

	@Transactional
	public Mart save(Mart mart) {
		return martRepository.findByRealIdAndMartType(mart.getRealId(), mart.getMartType())
				.map(savedMart -> savedMart.update(mart))
				.orElseGet(() -> martRepository.save(mart));
	}

	private Mart toEntity(MartData martData) {
		return Mart.builder()
				.martType(martData.getMartType())
				.realId(martData.getRealId())
				.branchName(martData.getBranchName())
				.phoneNumber(martData.getPhoneNumber())
				.region(martData.getRegion())
				.address(martData.getAddress())
				.openingHours(martData.getOpeningHours())
				.url(martData.getUrl())
				.holidays(martData.getHolidays().stream()
						.collect(toCollection(TreeSet::new)))
				.build();
	}

	private List<MartResponse> toResponses(List<Mart> marts) {
		return marts.stream()
				.map(MartResponse::new)
				.collect(toList());
	}
}
