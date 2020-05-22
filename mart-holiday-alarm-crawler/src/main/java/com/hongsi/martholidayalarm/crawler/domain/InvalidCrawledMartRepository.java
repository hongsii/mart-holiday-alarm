package com.hongsi.martholidayalarm.crawler.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvalidCrawledMartRepository extends JpaRepository<InvalidCrawledMart, Long> {

    List<InvalidCrawledMart> findAllByEnable(boolean enable);
}