package com.hongsi.martholidayalarm.scheduler.aspect;

import com.hongsi.martholidayalarm.utils.stopwatch.StopWatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CrawlerLoggingAspect {

	private StopWatch stopWatch;

	@Around("execution(* com.hongsi.martholidayalarm.scheduler.crawler.MartCrawlerScheduler.crawlMart())")
	public Object totalElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
        stopWatch = new StopWatch("MartCrawler");
		Object result = joinPoint.proceed();
		log.info(stopWatch.prettyPrint());
		return result;
	}

	@Around("execution(* com.hongsi.martholidayalarm.scheduler.crawler.model.MartCrawler+.crawl())")
	public Object recordEachElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
		String crawler = getClassName(joinPoint);
		log.info("[CRAWLING] start crawler : {}", crawler);
		stopWatch.start(crawler);
		Object result = joinPoint.proceed();
		stopWatch.stop(crawler);
		log.info("[CRAWLING] finished crawler : {}", crawler);
		return result;
	}

	private String getClassName(ProceedingJoinPoint joinPoint) {
		return joinPoint.getTarget().getClass().getSimpleName();
	}
}
