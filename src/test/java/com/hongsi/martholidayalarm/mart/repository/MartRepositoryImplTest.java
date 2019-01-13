package com.hongsi.martholidayalarm.mart.repository;

import static com.hongsi.martholidayalarm.mart.repository.MartRepositoryImpl.createOrderSpecifier;
import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.mart.domain.QMart;
import com.querydsl.core.types.OrderSpecifier;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class MartRepositoryImplTest {

	@Test
	public void orderBy_Expression_생성() {
		Sort sort = Sort.by(Order.asc("martType"), Order.asc("branchName"));

		final QMart martExpression = QMart.mart;
		assertThat(createOrderSpecifier(sort)).hasSize(2)
				.containsExactly(
						new OrderSpecifier(com.querydsl.core.types.Order.ASC,
								martExpression.martType),
						new OrderSpecifier(com.querydsl.core.types.Order.ASC,
								martExpression.branchName)
				);
	}
}