package com.hongsi.martholidayalarm.core.location

import com.hongsi.martholidayalarm.core.exception.LocationOutOfRangeException
import spock.lang.Specification

import static com.hongsi.martholidayalarm.core.location.Location.Range.Latitude
import static com.hongsi.martholidayalarm.core.location.Location.Range.Longitude

class LocationTest extends Specification {

    def "위도, 경도는 필수값이다"() {
        when:
        Location.of(latitude, longitude)

        then:
        thrown(IllegalArgumentException)

        where:
        latitude | longitude
        null     | 0
        0        | null
    }

    def "위도, 경도는 유효 범위에 포함돼야 한다"() {
        when:
        Location.of(latitude, longitude)

        then:
        thrown(LocationOutOfRangeException)

        where:
        latitude         | longitude
        Latitude.min - 1 | Longitude.min
        Latitude.max + 1 | Longitude.max
        Latitude.min     | Longitude.min - 1
        Latitude.max     | Longitude.max + 1
    }
}
