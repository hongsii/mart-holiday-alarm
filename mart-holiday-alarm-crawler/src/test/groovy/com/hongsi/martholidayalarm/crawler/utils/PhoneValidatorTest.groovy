package com.hongsi.martholidayalarm.crawler.utils


import spock.lang.Specification
import spock.lang.Unroll

class PhoneValidatorTest extends Specification {

    @Unroll
    def "Should validate phone number"() {
        expect:
        PhoneValidator.isValid(phoneNumber) == expected

        where:
        phoneNumber     || expected
        "02-1234-4560"  || true
        "051-1234-4560" || true
        "051-123-4560"  || true
        "+82-1899-9900" || true

        "02-12-4560"    || false
        "02-123-456"    || false
        "0233-123-4567" || false
    }
}
