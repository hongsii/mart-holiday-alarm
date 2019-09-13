package com.hongsi.martholidayalarm.scheduler.aspect;

import com.hongsi.martholidayalarm.client.slack.SlackNotifier;
import com.hongsi.martholidayalarm.client.slack.model.Color;
import com.hongsi.martholidayalarm.client.slack.model.Emoji;
import com.hongsi.martholidayalarm.client.slack.model.SlackMessage;
import com.hongsi.martholidayalarm.client.slack.model.SlackTextGenerator;
import com.hongsi.martholidayalarm.scheduler.crawler.model.mart.Crawlable;
import com.hongsi.martholidayalarm.utils.stopwatch.StopWatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CrawlerLoggingAspect {

	private final SlackNotifier slackNotifier;

	@Value("${spring.profiles.active}")
	private String activeProfile;
	private StopWatch stopWatch;

	@Around("execution(* com.hongsi.martholidayalarm.scheduler.crawler.MartCrawlerScheduler.crawlMart())")
	public Object totalElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
        stopWatch = new StopWatch("MartCrawler");
		Object result = joinPoint.proceed();
		log.info(stopWatch.prettyPrint());
		return result;
	}

	@Around("execution(* com.hongsi.martholidayalarm.scheduler.crawler.model.MartCrawler+.crawl())")
	public List<Crawlable> recordEachElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
		String crawler = getClassName(joinPoint);
		log.info("[CRAWLING] start crawler : {}", crawler);
		stopWatch.start(crawler);
		try {
			return (List<Crawlable>) joinPoint.proceed();
		} catch (Throwable e) {
			log.error("[CRAWLING] failed to crawling.", e);
			notifyToSlack(joinPoint, e);
		}
		stopWatch.stop(crawler);
		log.info("[CRAWLING] finished crawler : {}", crawler);
		return Collections.emptyList();
	}

	private void notifyToSlack(ProceedingJoinPoint joinPoint, Throwable e) {
		SlackMessage message = SlackMessage.error(
				SlackMessage.Attachment.builder()
						.text(Emoji.ZAP + " 크롤링 실패 " + Emoji.ZAP)
						.color(Color.RED)
						.fields(Arrays.asList(
								SlackMessage.Attachment.Field.builder()
										.title("Profile")
										.value(activeProfile)
										.shortField(true)
										.build(),
								SlackMessage.Attachment.Field.builder()
										.title("Target")
										.value(getClassName(joinPoint))
										.shortField(true)
										.build(),
								SlackMessage.Attachment.Field.builder()
										.title("Error")
										.value(e.toString())
										.shortField(true)
										.build(),
								SlackMessage.Attachment.Field.builder()
										.title("StackTrace")
										.value(SlackTextGenerator.codeBlock(getHalfStackTrace(e)))
										.shortField(false)
										.build()
						))
						.build()
		);
		slackNotifier.notify(message);
	}

	private String getHalfStackTrace(Throwable e) {
		StackTraceElement[] stackTrace = e.getStackTrace();
		return Arrays.stream(stackTrace)
				.limit(stackTrace.length / 2)
				.map(StackTraceElement::toString)
				.collect(Collectors.joining("\n"));
	}

	private String getClassName(JoinPoint joinPoint) {
		return joinPoint.getTarget().getClass().getSimpleName();
	}
}
