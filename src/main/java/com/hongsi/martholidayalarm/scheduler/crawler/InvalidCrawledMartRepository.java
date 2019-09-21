package com.hongsi.martholidayalarm.scheduler.crawler;

import com.hongsi.martholidayalarm.scheduler.crawler.domain.InvalidCrawledMart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvalidCrawledMartRepository extends JpaRepository<InvalidCrawledMart, Long> {

    List<InvalidCrawledMart> findAllByEnable(boolean enable);
}