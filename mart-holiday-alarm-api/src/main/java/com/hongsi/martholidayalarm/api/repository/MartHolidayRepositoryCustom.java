package com.hongsi.martholidayalarm.api.repository;

import com.hongsi.martholidayalarm.api.dto.mart.MartDto;
import com.hongsi.martholidayalarm.api.dto.mart.MartTypeDto;
import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.mart.MartType;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;

public interface MartHolidayRepositoryCustom {

    List<MartDto> findAllOrderBy(Sort sort);

    List<MartDto> findAllById(Collection<Long> ids, Sort sort);

    List<MartDto> findAllByMartType(MartType martType, Sort sort);

    List<MartDto> findAllByHoliday(Holiday holiday);

    List<MartTypeDto> findAllMartTypes();
}
