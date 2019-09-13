package com.hongsi.martholidayalarm.service;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.domain.push.PushMart;
import com.hongsi.martholidayalarm.exception.ResourceNotFoundException;
import com.hongsi.martholidayalarm.repository.MartRepository;
import com.hongsi.martholidayalarm.service.dto.mart.LocationDto;
import com.hongsi.martholidayalarm.service.dto.mart.MartDto;
import com.hongsi.martholidayalarm.service.dto.mart.MartTypeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MartService {

	private final MartRepository martRepository;

	public MartDto.Response findById(Long id) {
		Mart mart = martRepository.findById(id)
				.orElseThrow(ResourceNotFoundException::new);
		return MartDto.Response.of(mart);
	}

	public MartDto.Response findByMartTypeAndBranchName(MartType martType, String branchName) {
		return martRepository.findByMartTypeAndBranchName(martType, branchName)
				.map(MartDto.Response::of)
				.orElseThrow(EntityNotFoundException::new);
	}

	public List<MartDto.Response> findAll(Sort sort) {
		return toResponses(martRepository.findAllWithHoliday(sort));
	}

	public List<MartDto.Response> findAllById(Collection<Long> ids, Sort sort) {
		return toResponses(martRepository.findAllWithHolidayById(ids, sort));
	}

	public List<MartDto.Response> findAllByMartType(MartType martType, Sort sort) {
		return toResponses(martRepository.findAllByMartType(martType, sort));
	}

	public List<MartDto.Response> findAllByLocation(LocationDto.Request request) {
		Location location = request.toEntity();
		int withinDistance = request.getDistance();
		return toResponses(martRepository.findAllByLocation(location, withinDistance));
	}

	public List<MartTypeDto.Response> findMartTypes() {
		return martRepository.findMartTypes()
				.stream()
				.map(MartTypeDto.Response::of)
				.collect(toList());
	}

	public List<PushMart> findPushMartsByHoliday(Holiday holiday) {
	    return martRepository.findAllByHolidayInnerJoinHoliday(holiday).stream()
				.map(PushMart::from)
				.collect(Collectors.toList());
	}

	public List<String> findRegionsByMartType(MartType martType) {
		return martRepository.findRegionsByMartType(martType);
	}

	public List<String> findBranchNamesByMartTypeAndRegion(MartType martType, String region) {
		return martRepository.findBranchNamesByMartTypeAndRegion(martType, region);
	}

	@Transactional
	public List<Mart> saveAll(List<Mart> marts) {
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

	private List<MartDto.Response> toResponses(List<Mart> marts) {
		return marts.stream()
				.map(MartDto.Response::of)
				.collect(toList());
	}
}
