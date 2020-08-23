package com.hongsi.martholidayalarm.push.aspect;

import com.hongsi.martholidayalarm.client.slack.SlackNotifier;
import com.hongsi.martholidayalarm.client.slack.model.Color;
import com.hongsi.martholidayalarm.client.slack.model.SlackMessage;
import com.hongsi.martholidayalarm.push.model.PushCounter;
import com.hongsi.martholidayalarm.push.model.PushResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class PushLoggingAspect {

    private final SlackNotifier slackNotifier;

    @Value("${spring.profiles.active}")
    private String activeProfile;
    private StopWatch stopWatch;
    private PushCounter pushCounter;

    @Around("execution(* com.hongsi.martholidayalarm.push.MartPushScheduler.start())")
    public Object startPush(ProceedingJoinPoint joinPoint) throws Throwable {
        stopWatch = new StopWatch("Push");
        stopWatch.start();
        pushCounter = new PushCounter();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        notifyResult();
        return result;
    }

    @Around("execution(* com.hongsi.martholidayalarm.clients.firebase.message.FirebaseMessageSender.send(..))")
    public Object recordResult(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            result = joinPoint.proceed();
            pushCounter.recordSuccess();
        } catch (Throwable t) {
            log.error("failed to push.", t);
            pushCounter.recordFailure();
        }
        return result;
    }

    private void notifyResult() {
        PushResult pushResult = pushCounter.getPushResult();
        SlackMessage message = SlackMessage.success(
                "푸시 결과",
                SlackMessage.Attachment.builder()
                        .color(Color.LIME)
                        .fields(Arrays.asList(
                                SlackMessage.Attachment.Field.shortField("환경", activeProfile),
                                SlackMessage.Attachment.Field.shortField("소요시간", String.valueOf(stopWatch.getTotalTimeSeconds())),
                                SlackMessage.Attachment.Field.shortField("전송건수", String.valueOf(pushResult.getTotalCount())),
                                SlackMessage.Attachment.Field.shortField("성공건수", getFormattedCount(pushResult.getSuccessCount(), pushResult.getSuccessPercentage())),
                                SlackMessage.Attachment.Field.shortField("실패건수", getFormattedCount(pushResult.getFailureCount(), pushResult.getFailurePercentage()))
                        ))
                        .build()
        );
        slackNotifier.notify(message);
    }

    private String getFormattedCount(long count, long percentage) {
        return String.format("%d (%d%%)", count, percentage);
    }
}
