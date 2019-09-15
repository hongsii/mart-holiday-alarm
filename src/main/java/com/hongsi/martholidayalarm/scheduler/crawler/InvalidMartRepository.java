package com.hongsi.martholidayalarm.scheduler.crawler;

import com.hongsi.martholidayalarm.scheduler.crawler.domain.InvalidMart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidMartRepository extends JpaRepository<InvalidMart, Long> {
}