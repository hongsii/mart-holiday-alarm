package com.hongsi.martholidayalarm.scheduler.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class CrawlerLoggingAspect {

	private StopWatch stopWatch;

	@Around("execution(* com.hongsi.martholidayalarm.scheduler.crawler.MartCrawlerScheduler.crawlMart())")
	public Object totalElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[CRAWLING] start to crawl mart");
		stopWatch = new StopWatch(getClassName(joinPoint));
		Object result = joinPoint.proceed();
		log.info("[CRAWLING] finished to crawl mart");
		log.info(stopWatch.prettyPrint());
		return result;
	}

	@Around("execution(* com.hongsi.martholidayalarm.scheduler.crawler.model.MartCrawler+.crawl())")
	public Object recordEachElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
		stopWatch.start(getClassName(joinPoint));
		Object result = joinPoint.proceed();
		stopWatch.stop();
		return result;
	}

	private String getClassName(ProceedingJoinPoint joinPoint) {
		return joinPoint.getTarget().getClass().getSimpleName();
	}
}
