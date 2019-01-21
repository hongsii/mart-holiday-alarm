package com.hongsi.martholidayalarm.repository;

import static com.querydsl.core.types.dsl.MathExpressions.acos;
import static com.querydsl.core.types.dsl.MathExpressions.cos;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static com.querydsl.core.types.dsl.MathExpressions.sin;

import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.Mart;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.domain.mart.QHoliday;
import com.hongsi.martholidayalarm.domain.mart.QMart;
import com.hongsi.martholidayalarm.domain.push.PushMart;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
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
		return queryMartFetchHoliday(sort)
				.fetch();
	}

	@Override
	public List<Mart> findMartsById(Collection<Long> ids, Sort sort) {
		final QMart mart = QMart.mart;
		return queryMartFetchHoliday(sort)
				.where(mart.id.in(ids))
				.fetch();
	}

	@Override
	public List<Mart> findMartsByMartType(MartType martType, Sort sort) {
		final QMart mart = QMart.mart;
		return queryMartFetchHoliday(sort)
				.where(mart.martType.eq(martType))
				.fetch();
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

	@Override
	public List<Mart> findMartsByLocation(Location location, int withinDistance) {
		final QMart mart = QMart.mart;
		NumberPath<Double> latitude = mart.location.latitude;
		NumberPath<Double> longitude = mart.location.longitude;
		Expression<Double> currentLatitude = Expressions.constant(location.getLatitude());
		Expression<Double> currentLongitude = Expressions.constant(location.getLongitude());
		NumberExpression<Double> distanceFormula =
				acos(cos(radians(currentLatitude))
						.multiply(cos(radians(latitude)))
						.multiply(cos(radians(longitude)
								.subtract(radians(currentLongitude))))
						.add(sin(radians(currentLatitude))
								.multiply(sin(radians(latitude)))
						)
				)
						.multiply(Expressions.constant(6371));
		NumberPath<Double> distance = Expressions.numberPath(Double.class, "distance");

		List<Tuple> list = fromLeftJoinFetchHoliday()
				.select(mart, distanceFormula.as(distance))
				.where(distanceFormula.loe(withinDistance))
				.orderBy(distance.asc())
				.fetch();
		return list.stream()
				.map(tuple -> {
					Mart martTuple = tuple.get(mart);
					martTuple.getLocation().setDistance(tuple.get(distance));
					return martTuple;
				})
				.distinct()
				.collect(Collectors.toList());
	}

	private JPAQuery<Mart> queryMartFetchHoliday(Sort sort) {
		return queryMartFetchHoliday()
				.orderBy(createOrderSpecifier(sort));
	}

	private JPAQuery<Mart> queryMartFetchHoliday() {
		final QMart mart = QMart.mart;
		return fromLeftJoinFetchHoliday()
				.select(mart);

	}

	private JPAQuery<?> fromLeftJoinFetchHoliday() {
		final QMart mart = QMart.mart;
		final QHoliday holiday = QHoliday.holiday;
		return jpaQueryFactory.from(mart).leftJoin(mart.holidays, holiday)
				.fetchJoin()
				.distinct();
	}
}
