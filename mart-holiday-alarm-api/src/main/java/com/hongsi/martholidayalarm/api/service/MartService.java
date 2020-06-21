package com.hongsi.martholidayalarm.api.service;

import com.hongsi.martholidayalarm.api.dto.mart.LocationParameter;
import com.hongsi.martholidayalarm.api.dto.mart.MartDto;
import com.hongsi.martholidayalarm.api.dto.mart.MartTypeDto;
import com.hongsi.martholidayalarm.api.exception.ResourceNotFoundException;
import com.hongsi.martholidayalarm.api.repository.MartHolidayRepository;
import com.hongsi.martholidayalarm.api.repository.MartLocationRepository;
import com.hongsi.martholidayalarm.core.mart.Mart;
import com.hongsi.martholidayalarm.core.mart.MartType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MartService {

    private final MartHolidayRepository martHolidayRepository;
    private final MartLocationRepository martLocationRepository;

    public List<MartDto> findAll(Sort sort) {
        return martHolidayRepository.findAllOrderBy(sort);
    }

    public List<MartDto> findAllById(Collection<Long> ids, Sort sort) {
        return martHolidayRepository.findAllById(ids, sort);
    }

    public MartDto findById(Long id) {
        Mart mart = martHolidayRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return new MartDto(mart);
    }

    public List<MartDto> findAllByMartType(MartType martType, Sort sort) {
        return martHolidayRepository.findAllByMartType(martType, sort);
    }

    public List<MartDto> findAllByLocation(LocationParameter parameter) {
        return martLocationRepository.findAllByLocation(parameter.getLatitude(), parameter.getLongitude(), parameter.getDistance());
    }

    public List<MartTypeDto> findAllMartTypes() {
        return martHolidayRepository.findAllMartTypes();
    }
}
