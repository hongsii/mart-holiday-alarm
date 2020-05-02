package com.hongsi.martholidayalarm.api.repository;

import com.hongsi.martholidayalarm.api.dto.mart.MartDto;
import com.hongsi.martholidayalarm.api.dto.mart.MartTypeDto;
import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.mart.Mart;
import com.hongsi.martholidayalarm.core.mart.MartType;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.hongsi.martholidayalarm.core.holiday.QHoliday.holiday;
import static com.hongsi.martholidayalarm.core.mart.QMart.mart;

@Repository
public class MartHolidayRepositoryImpl extends BaseQuerydslRepositorySupport implements MartHolidayRepositoryCustom {

    public MartHolidayRepositoryImpl() {
        super(Mart.class);
    }

    @Override
    public List<MartDto> findAllOrderBy(Sort sort) {
        return applySorting(sort, query -> query
                .select(Projections.constructor(MartDto.class, mart))
                .from(mart)
                .distinct()
                .leftJoin(mart.holidays, holiday).fetchJoin())
                .fetch();
    }

    @Override
    public List<MartDto> findAllById(Collection<Long> ids, Sort sort) {
        return applySorting(sort, query -> query
                .select(Projections.constructor(MartDto.class, mart))
                .from(mart)
                .distinct()
                .leftJoin(mart.holidays, holiday).fetchJoin()
                .where(mart.id.in(ids)))
                .fetch();
    }

    @Override
    public List<MartDto> findAllByHoliday(Holiday condition) {
        return select(Projections.constructor(MartDto.class, mart))
                .from(mart)
                .distinct()
                .innerJoin(mart.holidays, holiday).fetchJoin()
                .where(holiday.date.eq(condition.getDate()))
                .fetch();
    }

    @Override
    public List<MartDto> findAllByMartType(MartType martType, Sort sort) {
        return applySorting(sort, query -> query
                .select(Projections.constructor(MartDto.class, mart))
                .from(mart)
                .leftJoin(mart.holidays, holiday).fetchJoin()
                .where(mart.martType.eq(martType)))
                .fetch();
    }

    @Override
    public List<MartTypeDto> findAllMartTypes() {
        return select(Projections.constructor(MartTypeDto.class, mart.martType))
                .from(mart)
                .groupBy(mart.martType)
                .orderBy(mart.martType.asc())
                .fetch();
    }
}
