package com.hongsi.martholidayalarm.mart.service;

import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.repository.MartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
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
}
