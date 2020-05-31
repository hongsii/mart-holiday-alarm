package com.hongsi.martholidayalarm.push.aspect;

import com.hongsi.martholidayalarm.client.slack.SlackNotifier;
import com.hongsi.martholidayalarm.client.slack.model.Color;
import com.hongsi.martholidayalarm.client.slack.model.SlackMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class PushLoggingAspect {

    private final SlackNotifier slackNotifier;

    @Value("${spring.profiles.active}")
    private String activeProfile;
    private StopWatch stopWatch;
    private AtomicInteger totalCount;
    private AtomicInteger successCount;

    @Around("execution(* com.hongsi.martholidayalarm.push.MartPushScheduler.start())")
    public Object totalElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
        stopWatch = new StopWatch("Push");
        stopWatch.start();
        totalCount = new AtomicInteger(0);
        successCount = new AtomicInteger(0);

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        notifyResult();
        return result;
    }

    @Around("execution(* com.hongsi.martholidayalarm.clients.firebase.message.FirebaseMessageSender.send(..))")
    public Object recordSuccessCount(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            totalCount.incrementAndGet();
            result = joinPoint.proceed();
            successCount.incrementAndGet();
        } catch (Throwable t) {
            log.error("failed to push.", t);
        }
        return result;
    }

    private void notifyResult() {
        int total = totalCount.get();
        int success = successCount.get();
        SlackMessage message = SlackMessage.success(
                "푸시 결과",
                SlackMessage.Attachment.builder()
                        .color(Color.LIME)
                        .fields(Arrays.asList(
                                SlackMessage.Attachment.Field.builder()
                                        .title("환경")
                                        .value(activeProfile)
                                        .shortField(true)
                                        .build(),
                                SlackMessage.Attachment.Field.builder()
                                        .title("소요시간")
                                        .value(String.valueOf(stopWatch.getTotalTimeSeconds()))
                                        .shortField(true)
                                        .build(),
                                SlackMessage.Attachment.Field.builder()
                                        .title("전송건수")
                                        .value(String.valueOf(total))
                                        .shortField(true)
                                        .build(),
                                SlackMessage.Attachment.Field.builder()
                                        .title("성공건수")
                                        .value(String.format("%d (%d%%)", success, calculateSuccessPercentage(total, success)))
                                        .shortField(true)
                                        .build()
                        ))
                        .build()
        );
        slackNotifier.notify(message);
    }

    private int calculateSuccessPercentage(int total, int success) {
        if (success == 0) {
            return 0;
        }
        return (success / total) * 100;
    }
}
