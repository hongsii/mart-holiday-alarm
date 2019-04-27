package com.hongsi.martholidayalarm.repository;

import com.hongsi.martholidayalarm.domain.mart.*;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.dsl.MathExpressions.*;

@AllArgsConstructor
public class MartRepositoryImpl implements MartRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private final QMart mart = QMart.mart;
	private final QHoliday holiday = QHoliday.holiday;

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
	public List<Mart> findAllWithHoliday(Sort sort) {
		return selectLeftJoinHoliday(sort)
				.fetch();
	}

	@Override
	public List<Mart> findAllWithHolidayById(Collection<Long> ids, Sort sort) {
		return selectLeftJoinHoliday(sort)
				.where(mart.id.in(ids))
				.fetch();
	}

	@Override
	public List<Mart> findAllByHolidayInnerJoinHoliday(Holiday condition) {
		return selectInnerJoinHoliday()
				.where(holiday.date.eq(condition.getDate()))
				.fetch();
	}

	@Override
	public List<Mart> findAllByLocation(Location location, int withinDistance) {
		NumberExpression<Double> distanceFormula = makeDistanceFormulaFromLocation(location);
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

	private NumberExpression<Double> makeDistanceFormulaFromLocation(Location location) {
		Expression<Double> currentLatitude = Expressions.constant(location.getLatitude());
		Expression<Double> currentLongitude = Expressions.constant(location.getLongitude());
		final Expression<Integer> TO_KILOMETER = Expressions.constant(6371);
		return acos(
				cos(radians(currentLatitude))
						.multiply(cos(radians(mart.location.latitude)))
						.multiply(cos(radians(mart.location.longitude)
								.subtract(radians(currentLongitude)))
						)
						.add(sin(radians(currentLatitude))
								.multiply(sin(radians(mart.location.latitude)))
						)
		)
				.multiply(TO_KILOMETER);
	}


	@Override
	public List<Mart> findAllByMartType(MartType martType, Sort sort) {
		return selectLeftJoinHoliday(sort)
				.where(mart.martType.eq(martType))
				.fetch();
	}

	@Override
	public List<MartType> findMartTypes() {
		return jpaQueryFactory.query()
				.select(mart.martType)
				.from(mart)
				.groupBy(mart.martType)
				.orderBy(mart.martType.asc())
				.fetch();
	}

	@Override
	public List<String> findRegionsByMartType(MartType martType) {
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
		return jpaQueryFactory.query()
				.select(mart.branchName)
				.from(mart)
				.where(mart.martType.eq(martType)
						.and(mart.region.eq(region)))
				.groupBy(mart.martType, mart.branchName)
				.orderBy(mart.branchName.asc())
				.fetch();
	}

	private JPAQuery<Mart> selectLeftJoinHoliday(Sort sort) {
		return selectLeftJoinHoliday()
				.orderBy(createOrderSpecifier(sort));
	}

	private JPAQuery<Mart> selectInnerJoinHoliday() {
		return jpaQueryFactory
				.selectFrom(mart).innerJoin(mart.holidays, holiday)
				.fetchJoin()
				.distinct();
	}

	private JPAQuery<Mart> selectLeftJoinHoliday() {
		return fromLeftJoinFetchHoliday()
				.select(mart);
	}

	private JPAQuery<?> fromLeftJoinFetchHoliday() {
		return jpaQueryFactory.from(mart).leftJoin(mart.holidays, holiday)
				.fetchJoin()
				.distinct();
	}
}
