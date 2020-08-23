package com.hongsi.martholidayalarm.push.model

import spock.lang.Specification
import spock.lang.Unroll

class PushResultTest extends Specification {

    @Unroll
    def "Should calculate success percentage"() {
        given:
        def result = PushResult.builder()
                .successCount(success)
                .failureCount(failure)
                .build()

        expect:
        result.getSuccessPercentage() == successPercentage
        result.getFailurePercentage() == failurePercentage

        where:
        success | failure || successPercentage | failurePercentage
        3       | 6       || 33                | 67
        2       | 8       || 20                | 80
        0       | 5       || 0                 | 100
        0       | 0       || 0                 | 0
    }
}
