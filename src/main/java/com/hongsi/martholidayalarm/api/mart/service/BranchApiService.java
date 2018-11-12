package com.hongsi.martholidayalarm.api.mart.service;

import com.hongsi.martholidayalarm.api.mart.dto.MartResponseDto;
import com.hongsi.martholidayalarm.api.mart.repository.MartApiRepository;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BranchApiService {

	private final MartApiRepository martApiRepository;

	public List<MartResponseDto> getMartsById(Iterable<Long> ids) {
		return toDto(martApiRepository.findAllById(ids));
	}

	private List<MartResponseDto> toDto(List<Mart> marts) {
		return marts.stream()
				.map(MartResponseDto::new)
				.collect(Collectors.toList());
	}
}
