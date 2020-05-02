package com.hongsi.martholidayalarm.api.repository;

import com.hongsi.martholidayalarm.api.dto.mart.MartDto;
import com.hongsi.martholidayalarm.core.mart.Mart;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.hongsi.martholidayalarm.core.holiday.QHoliday.holiday;
import static com.hongsi.martholidayalarm.core.mart.QMart.mart;
import static com.querydsl.core.types.dsl.MathExpressions.*;

@Repository
public class MartLocationRepository extends BaseQuerydslRepositorySupport {

    public MartLocationRepository() {
        super(Mart.class);
    }

    public List<MartDto> findAllByLocation(Double latitude, Double longitude, Integer distance) {
        NumberExpression<Double> distanceFormula = distanceFormula(latitude, longitude);
        NumberPath<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return select(Projections.constructor(MartDto.class, mart, distanceFormula.as(distancePath)))
                .from(mart)
                .leftJoin(mart.holidays, holiday).fetchJoin()
                .where(distanceFormula.loe(distance))
                .orderBy(distancePath.asc())
                .fetch();
    }

    private NumberExpression<Double> distanceFormula(Double latitude, Double longitude) {
        Expression<Double> currentLatitude = Expressions.constant(latitude);
        Expression<Double> currentLongitude = Expressions.constant(longitude);
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
        ).multiply(TO_KILOMETER);
    }
}
