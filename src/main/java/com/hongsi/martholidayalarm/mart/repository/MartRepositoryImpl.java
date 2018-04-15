package com.hongsi.martholidayalarm.mart.repository;

import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.domain.QMart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MartRepositoryImpl implements MartRepositoryCustom {

	JPAQueryFactory queryFactory;

	@Override
	public List<String> findRegionByMartType(MartType martType) {
		QMart mart = QMart.mart;
		return queryFactory.query()
				.select(mart.region)
				.from(mart)
				.where(mart.martType.eq(martType))
				.groupBy(mart.martType, mart.region)
				.orderBy(mart.region.asc())
				.fetch();
	}
}
