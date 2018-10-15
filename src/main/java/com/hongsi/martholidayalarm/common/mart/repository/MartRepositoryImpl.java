package com.hongsi.martholidayalarm.common.mart.repository;

import com.hongsi.martholidayalarm.common.mart.domain.Holiday;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.common.mart.domain.QHoliday;
import com.hongsi.martholidayalarm.common.mart.domain.QMart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MartRepositoryImpl implements MartRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<MartType> findMartType() {
		QMart mart = QMart.mart;
		return jpaQueryFactory.query()
				.select(mart.martType)
				.from(mart)
				.groupBy(mart.martType)
				.orderBy(mart.martType.asc())
				.fetch();
	}

	@Override
	public List<String> findRegionByMartType(MartType martType) {
		QMart mart = QMart.mart;
		return jpaQueryFactory.query()
				.select(mart.region)
				.from(mart)
				.where(mart.martType.eq(martType))
				.groupBy(mart.martType, mart.region)
				.orderBy(mart.region.asc())
				.fetch();
	}

	@Override
	public List<String> findBranchByMartTypeAndRegion(MartType martType, String region) {
		QMart mart = QMart.mart;
		return jpaQueryFactory.query()
				.select(mart.branchName)
				.from(mart)
				.where(mart.martType.eq(martType)
						.and(mart.region.eq(region)))
				.groupBy(mart.martType, mart.branchName)
				.orderBy(mart.branchName.asc())
				.fetch();
	}

	@Override
	public LocalDateTime findMaxModifiedDate() {
		QMart mart = QMart.mart;
		return jpaQueryFactory.query()
				.select(mart.modifiedDate.max())
				.from(mart)
				.fetchOne();
	}

	@Override
	public List<Mart> findByModifiedDateLessThanOrEqual(LocalDateTime conditionTime) {
		QMart mart = QMart.mart;
		return jpaQueryFactory
				.selectFrom(mart)
				.where(mart.modifiedDate.loe(conditionTime))
				.fetch();
	}

	@Override
	public List<Mart> findHavingSameHoliday(Holiday holidayParam) {
		QMart mart = QMart.mart;
		QHoliday holiday = QHoliday.holiday;

		return jpaQueryFactory
				.select(mart)
				.from(mart).innerJoin(holiday).on(holiday.mart.id.eq(mart.id))
				.where(holiday.date.eq(holidayParam.getDate()))
				.fetch();
	}
}
