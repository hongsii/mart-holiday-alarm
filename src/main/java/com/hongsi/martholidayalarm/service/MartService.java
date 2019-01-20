package com.hongsi.martholidayalarm.service;

import static java.util.stream.Collectors.toList;

import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartData;
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
import java.util.Collection;
import java.util.List;
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

	public List<MartDto.Response> findMarts(Sort sort) {
		return toResponses(martRepository.findMarts(sort));
	}

	public List<MartDto.Response> findMartsById(Collection<Long> ids, Sort sort) {
		return toResponses(martRepository.findMartsById(ids, sort));
	}

	public MartDto.Response findMartById(Long id) {
		Mart mart = martRepository.findById(id)
				.orElseThrow(ResourceNotFoundException::new);
		return MartDto.Response.of(mart);
	}

	public List<MartTypeDto.Response> findMartTypes() {
		return martRepository.findMartTypesByGrouping()
				.stream()
				.map(MartTypeDto.Response::of)
				.collect(toList());
	}

	public List<MartDto.Response> findMartsByMartType(MartType martType, Sort sort) {
		return toResponses(martRepository.findMartsByMartType(martType, sort));
	}

	public List<PushMart> findPushMartsByHoliday(Holiday holiday) {
		return martRepository.findPushMartsByHoliday(holiday);
	}

	public MartDto.Response findMartByMartTypeAndBranchName(MartType martType, String branchName) {
		return martRepository.findByMartTypeAndBranchName(martType, branchName)
				.map(MartDto.Response::of)
				.orElseThrow(EntityNotFoundException::new);
	}

	public List<String> findRegionsByMartType(MartType martType) {
		return martRepository.findRegionsByMartType(martType);
	}

	public List<String> findBranchNamesByMartTypeAndRegion(MartType martType, String region) {
		return martRepository.findBranchNamesByMartTypeAndRegion(martType, region);
	}

	public List<MartDto.Response> findMartsByLocation(LocationDto.Request request) {
		Location location = request.toEntity();
		int withinDistance = request.getDistance();
		return toResponses(martRepository.findMartsByLocation(location, withinDistance));
	}

	@Transactional
	public List<Mart> saveCrawlerMarts(List<CrawlerMartData> crawlerMartData) {
		return crawlerMartData.stream()
				.map(CrawlerMartData::toEntity)
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
