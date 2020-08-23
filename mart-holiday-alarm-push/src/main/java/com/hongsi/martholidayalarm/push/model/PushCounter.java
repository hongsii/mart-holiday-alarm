package com.hongsi.martholidayalarm.push.model;

import java.util.concurrent.atomic.LongAdder;

public class PushCounter {

    private LongAdder successCount;
    private LongAdder failureCount;

    public PushCounter() {
        this.successCount = new LongAdder();
        this.failureCount = new LongAdder();
    }

    public void recordSuccess() {
        successCount.add(1);
    }

    public void recordFailure() {
        failureCount.add(1);
    }

    public PushResult getPushResult() {
        long success = successCount.sum();
        long failure = failureCount.sum();
        return PushResult.builder()
                .successCount(success)
                .failureCount(failure)
                .build();
    }
}
