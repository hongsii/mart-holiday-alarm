package com.hongsi.martholidayalarm.mart.repository;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.domain.QHoliday;
import com.hongsi.martholidayalarm.mart.domain.QMart;
import com.hongsi.martholidayalarm.mart.dto.PushMart;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MartRepositoryImpl implements MartRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<MartType> findMartTypesByGrouping() {
		QMart mart = QMart.mart;
		return jpaQueryFactory.query()
				.select(mart.martType)
				.from(mart)
				.groupBy(mart.martType)
				.orderBy(mart.martType.asc())
				.fetch();
	}

	@Override
	public List<String> findRegionsByMartType(MartType martType) {
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
	public List<String> findBranchNamesByMartTypeAndRegion(MartType martType, String region) {
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
	public List<PushMart> findPushMartsByHoliday(Holiday targetHoliday) {
		QMart mart = QMart.mart;
		QHoliday holiday = QHoliday.holiday;
		return jpaQueryFactory
				.select(Projections
						.constructor(PushMart.class, mart.id, mart.martType, mart.branchName,
								holiday))
				.from(mart)
				.innerJoin(mart.holidays, holiday)
				.where(holiday.date.eq(targetHoliday.getDate()))
				.fetch();
	}
}
