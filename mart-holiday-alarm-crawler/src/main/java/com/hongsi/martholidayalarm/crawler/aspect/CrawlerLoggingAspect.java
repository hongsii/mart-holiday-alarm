package com.hongsi.martholidayalarm.crawler.aspect;

import com.hongsi.martholidayalarm.client.slack.SlackNotifier;
import com.hongsi.martholidayalarm.client.slack.model.Color;
import com.hongsi.martholidayalarm.client.slack.model.Emoji;
import com.hongsi.martholidayalarm.client.slack.model.SlackMessage;
import com.hongsi.martholidayalarm.client.slack.model.SlackTextGenerator;
import com.hongsi.martholidayalarm.crawler.model.MartParser;
import com.hongsi.martholidayalarm.crawler.utils.StopWatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
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
    private Map<String, Integer> results;

    @Around("execution(* com.hongsi.martholidayalarm.crawler.MartCrawlerScheduler.crawlMarts())")
    public Object totalElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
        stopWatch = new StopWatch("MartCrawler");
        results = new LinkedHashMap<>();
        Object result = joinPoint.proceed();
        log.info(stopWatch.prettyPrint());
        notifyResult(joinPoint);
        return result;
    }

    @Around("execution(* com.hongsi.martholidayalarm.crawler.MartCrawler+.crawl())")
    public List<? extends MartParser> recordEachElapsedTime(ProceedingJoinPoint joinPoint) {
        String crawler = getClassName(joinPoint);
        log.info("start crawling. crawler: {}", crawler);
        stopWatch.start(crawler);
        try {
            List<MartParser> result = (List<MartParser>) joinPoint.proceed();
            results.put(crawler, result.size());
            log.info("finished crawling. crawler: {}, total count: {}", crawler, result.size());
            return result;
        } catch (Throwable e) {
            log.error("failed crawling. crawler: {}", crawler, e);
            notifyError(joinPoint, e);
        } finally {
            stopWatch.stop(crawler);
        }
        return Collections.emptyList();
    }

    private void notifyResult(ProceedingJoinPoint joinPoint) {
        SlackMessage message = SlackMessage.success(
                "크롤링 결과",
                SlackMessage.Attachment.builder()
                        .color(Color.LIME)
                        .fields(Arrays.asList(
                                SlackMessage.Attachment.Field.builder()
                                        .title("환경")
                                        .value(activeProfile)
                                        .shortField(true)
                                        .build(),
                                SlackMessage.Attachment.Field.builder()
                                        .title("대상")
                                        .value(getClassName(joinPoint))
                                        .shortField(true)
                                        .build(),
                                SlackMessage.Attachment.Field.builder()
                                        .title("소요시간")
                                        .value(stopWatch.prettyPrint())
                                        .shortField(false)
                                        .build(),
                                SlackMessage.Attachment.Field.builder()
                                        .title("크롤링 수")
                                        .value(joinResults())
                                        .shortField(false)
                                        .build()
                        ))
                        .build()
        );
        slackNotifier.notify(message);
    }

    private String joinResults() {
        return results.entrySet().stream()
                .map(entry -> String.format("%s : %s", entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    private void notifyError(ProceedingJoinPoint joinPoint, Throwable e) {
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
