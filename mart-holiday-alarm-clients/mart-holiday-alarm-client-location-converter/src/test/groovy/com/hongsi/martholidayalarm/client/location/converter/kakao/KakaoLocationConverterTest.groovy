package com.hongsi.martholidayalarm.client.location.converter.kakao

import com.hongsi.martholidayalarm.client.location.converter.LocationConversion
import com.hongsi.martholidayalarm.client.location.converter.LocationConverterProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.util.UriUtils
import spock.lang.Specification

import static org.springframework.test.annotation.DirtiesContext.ClassMode
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

@RestClientTest(value = [KakaoLocationConverter.class])
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class KakaoLocationConverterTest extends Specification {

    private static final String searchUrl = "https://dapi.kakao.com/v2/local/convert/keyword.json"
    private static final String addressUrl = "https://dapi.kakao.com/v2/local/search/address.json"

    @Autowired
    private MockRestServiceServer mockServer

    @Autowired
    private RestTemplateBuilder restTemplateBuilder

    private KakaoLocationConverter kakaoLocationConverter

    void setup() {
        LocationConverterProperties clientInfo = LocationConverterProperties.builder()
                .clientId("test")
                .searchUrl(searchUrl)
                .addressUrl(addressUrl)
                .build()
        kakaoLocationConverter = new KakaoLocationConverter(restTemplateBuilder, clientInfo)
    }

    def "Should convert by query supplier"() {
        given:
        String expected = "{ \"documents\": [ { \"address_name\": \"서울 송파구 잠실동 44-1\", \"category_group_code\": \"mt1\", \"category_group_name\": \"대형마트\", \"category_name\": \"가정,생활 > 슈퍼마켓 > 대형슈퍼 > 홈플러스익스프레스\", \"distance\": \"\", \"id\": \"16705428\", \"phone\": \"02-418-8545\", \"place_name\": \"홈플러스익스프레스 잠실점\", \"place_url\": \"http://place.map.daum.net/16705428\", \"road_address_name\": \"서울 송파구 석촌호수로 133\", \"x\": \"127.09182075113635\", \"y\": \"37.507577456249656\" } ], \"meta\": { \"is_end\": true, \"pageable_count\": 1, \"same_name\": { \"keyword\": \"홈플러스 익스프레스 잠실점\", \"region\": [], \"selected_region\": \"\" }, \"total_count\": 1 } }"
        def query = "홈플러스 익스프레스 잠실점"
        mockServer.expect(ExpectedCount.times(1), requestTo(getRequestUri(searchUrl, query)))
                .andRespond(withSuccess(expected, MediaType.APPLICATION_JSON))

        when:
        def result = kakaoLocationConverter.convert({ "홈플러스 익스프레스 잠실점" }, { "not use" })

        then:
        result.latitude == 37.507577456249656
        result.longitude == 127.09182075113635
    }

    def "Should request by fallback if query request failed"() {
        given:
        String failResponse = "{ \"documents\": [], \"meta\": { \"is_end\": true, \"pageable_count\": 0, \"same_name\": { \"keyword\": \"홈플러스 익스프레스 없는 마트\", \"region\": [], \"selected_region\": \"\" }, \"total_count\": 0 } }\n"
        def query = "홈플러스 익스프레스 없는 마트"
        mockServer.expect(ExpectedCount.times(1), requestTo(getRequestUri(searchUrl, query)))
                .andRespond(withSuccess(failResponse, MediaType.APPLICATION_JSON))
        String expected = "{ \"documents\": [ { \"address_name\": \"서울 송파구 잠실동 44-1\", \"category_group_code\": \"mt1\", \"category_group_name\": \"대형마트\", \"category_name\": \"가정,생활 > 슈퍼마켓 > 대형슈퍼 > 홈플러스익스프레스\", \"distance\": \"\", \"id\": \"16705428\", \"phone\": \"02-418-8545\", \"place_name\": \"홈플러스익스프레스 잠실점\", \"place_url\": \"http://place.map.daum.net/16705428\", \"road_address_name\": \"서울 송파구 석촌호수로 133\", \"x\": \"127.09182075113635\", \"y\": \"37.507577456249656\" } ], \"meta\": { \"is_end\": true, \"pageable_count\": 1, \"same_name\": { \"keyword\": \"홈플러스 익스프레스 잠실점\", \"region\": [], \"selected_region\": \"\" }, \"total_count\": 1 } }"
        def fallback = "서울시 구로구 우리집"
        mockServer.expect(ExpectedCount.times(1), requestTo(getRequestUri(addressUrl, fallback)))
                .andRespond(withSuccess(expected, MediaType.APPLICATION_JSON))

        when:
        def result = kakaoLocationConverter.convert({ query }, { fallback })

        then:
        result.latitude == 37.507577456249656
        result.longitude == 127.09182075113635
    }

    def "Should return empty if all requests failed"() {
        given:
        def expected = "{ \"documents\": [], \"meta\": { \"is_end\": true, \"pageable_count\": 0, \"same_name\": { \"keyword\": \"홈플러스 익스프레스 없는 마트\", \"region\": [], \"selected_region\": \"\" }, \"total_count\": 0 } }\n"
        def query = "홈플러스 익스프레스 없는 마트"
        mockServer.expect(ExpectedCount.times(1), requestTo(getRequestUri(searchUrl, query)))
                .andRespond(withSuccess(expected, MediaType.APPLICATION_JSON))
        def fallback = "서울시 구로구 우리집"
        mockServer.expect(ExpectedCount.times(1), requestTo(getRequestUri(addressUrl, fallback)))
                .andRespond(withSuccess(expected, MediaType.APPLICATION_JSON))

        when:
        def result = kakaoLocationConverter.convert({ query }, { fallback })

        then:
        result == LocationConversion.EMPTY
    }

    private static def getRequestUri(String baseUrl, String value) {
        return "$baseUrl?query=${UriUtils.encode(value, "UTF-8")}&page=1&size=5"
    }
}
