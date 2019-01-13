package com.hongsi.martholidayalarm.mart.repository;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.domain.QHoliday;
import com.hongsi.martholidayalarm.mart.domain.QMart;
import com.hongsi.martholidayalarm.mart.dto.PushMart;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
public class MartRepositoryImpl implements MartRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;

	public static OrderSpecifier[] createOrderSpecifier(Sort sort) {
		PathBuilder orderByExpression = new PathBuilder(Mart.class, "mart");
		return sort.get()
				.map(order -> new OrderSpecifier(
						order.isAscending() ? com.querydsl.core.types.Order.ASC
								: com.querydsl.core.types.Order.DESC,
						orderByExpression.get(order.getProperty())))
				.toArray(OrderSpecifier[]::new);
	}

	@Override
	public List<Mart> findMarts(Sort sort) {
		return queryMartFetchingHoliday(sort)
				.fetch();
	}

	@Override
	public List<Mart> findMartsById(Collection<Long> ids, Sort sort) {
		final QMart mart = QMart.mart;
		return queryMartFetchingHoliday(sort)
				.where(mart.id.in(ids))
				.fetch();
	}

	@Override
	public List<Mart> findMartsByMartType(MartType martType, Sort sort) {
		final QMart mart = QMart.mart;
		return queryMartFetchingHoliday(sort)
				.where(mart.martType.eq(martType))
				.fetch();
	}

	private JPAQuery<Mart> queryMartFetchingHoliday(Sort sort) {
		final QMart mart = QMart.mart;
		final QHoliday holiday = QHoliday.holiday;
		return jpaQueryFactory.selectFrom(mart)
				.leftJoin(mart.holidays, holiday).fetchJoin()
				.orderBy(createOrderSpecifier(sort))
				.distinct();
	}

	@Override
	public List<MartType> findMartTypesByGrouping() {
		final QMart mart = QMart.mart;
		return jpaQueryFactory.query()
				.select(mart.martType)
				.from(mart)
				.groupBy(mart.martType)
				.orderBy(mart.martType.asc())
				.fetch();
	}

	@Override
	public List<String> findRegionsByMartType(MartType martType) {
		final QMart mart = QMart.mart;
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
		final QMart mart = QMart.mart;
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
		final QMart mart = QMart.mart;
		final QHoliday holiday = QHoliday.holiday;
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
