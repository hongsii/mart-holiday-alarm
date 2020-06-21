package com.hongsi.martholidayalarm.push.repository;

import com.hongsi.martholidayalarm.core.BaseQuerydslRepositorySupport;
import com.hongsi.martholidayalarm.core.mart.Mart;
import com.hongsi.martholidayalarm.push.model.PushMart;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.hongsi.martholidayalarm.core.holiday.QHoliday.holiday;
import static com.hongsi.martholidayalarm.core.mart.QMart.mart;

@Repository
public class PushMartRepositoryImpl extends BaseQuerydslRepositorySupport implements PushMartRepositoryCustom {

    public PushMartRepositoryImpl() {
        super(Mart.class);
    }

    @Override
    public List<PushMart> findAllByIdInAndHolidayDate(List<Long> ids, LocalDate holidayDate) {
        return select(mart)
                .from(mart)
                .distinct()
                .innerJoin(mart.holidays, holiday).fetchJoin()
                .where(mart.id.in(ids).and(holiday.date.eq(holidayDate)))
                .fetch()
                .stream()
                .map(PushMart::from)
                .collect(Collectors.toList());
    }
}
