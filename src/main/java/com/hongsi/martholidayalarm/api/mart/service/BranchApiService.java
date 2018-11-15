package com.hongsi.martholidayalarm.api.mart.service;

import com.hongsi.martholidayalarm.api.exception.ResourceNotFoundException;
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

	public MartResponseDto getMartById(Long id) throws ResourceNotFoundException {
		Mart mart = martApiRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
		return new MartResponseDto(mart);
	}

	public List<MartResponseDto> getMartsById(Iterable<Long> ids) throws ResourceNotFoundException {
		List<Mart> marts = martApiRepository.findAllById(ids);
		if (marts.isEmpty()) {
			throw new ResourceNotFoundException();
		}
		return toDto(marts);
	}

	private List<MartResponseDto> toDto(List<Mart> marts) {
		return marts.stream()
				.map(MartResponseDto::new)
				.collect(Collectors.toList());
	}
}
