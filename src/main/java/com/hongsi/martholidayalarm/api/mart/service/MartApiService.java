package com.hongsi.martholidayalarm.api.mart.service;

import com.hongsi.martholidayalarm.api.mart.dto.MartResponseDto;
import com.hongsi.martholidayalarm.api.mart.repository.MartApiRepository;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MartApiService {

	private final MartApiRepository martApiRepository;

	public List<MartResponseDto> getMartsByMartType(MartType martType) {
		return toDto(martApiRepository.findByMartType(martType));
	}

	private List<MartResponseDto> toDto(List<Mart> marts) {
		return marts.stream()
				.map(MartResponseDto::new)
				.collect(Collectors.toList());
	}
}
