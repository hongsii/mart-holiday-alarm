package com.hongsi.martholidayalarm.push.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class PushResult {

    private long totalCount;
    private long successCount;
    private long failureCount;

    @Builder
    private PushResult(long successCount, long failureCount) {
        this.totalCount = successCount + failureCount;
        this.successCount = successCount;
        this.failureCount = failureCount;
    }

    public long getSuccessPercentage() {
        return Math.round(successCount * 100.0 / totalCount);
    }

    public long getFailurePercentage() {
        return Math.round(failureCount * 100.0 / totalCount);
    }
}
