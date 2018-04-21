package com.hongsi.martholidayalarm.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JPAConfig {

	@PersistenceContext
	EntityManager em;

	@Bean
	public JPAQueryFactory queryFactory() {
		return new JPAQueryFactory(em);
	}
}
