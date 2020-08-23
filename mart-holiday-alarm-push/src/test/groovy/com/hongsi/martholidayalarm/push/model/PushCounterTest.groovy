package com.hongsi.martholidayalarm.push.model


import spock.lang.Specification

class PushCounterTest extends Specification {

    def "Should record push result"() {
        given:
        def counter = new PushCounter()
        def successCount = 2
        def failureCount = 3

        when:
        (1..successCount).forEach { counter.recordSuccess() }
        (1..failureCount).forEach { counter.recordFailure() }

        then:
        def expected = PushResult.builder()
                .successCount(successCount)
                .failureCount(failureCount)
                .build()
        counter.getPushResult() == expected
    }
}
