package com.hongsi.martholidayalarm.crawler.utils

import spock.lang.Specification

import java.util.regex.Pattern
import java.util.stream.Collectors

class MatchSpliteratorTest extends Specification {

    def "Should collect all matched text"() {
        given:
        def pattern = Pattern.compile("test(\\d+)")
        def targetTexts = ["test1", "test2", "test3"]
        def nonTargetTexts = ["tes1t", "test"]

        when:
        def matchSpliterator = MatchSpliterator.from(pattern, (targetTexts + nonTargetTexts).join(" "))
        def words = matchSpliterator.stream().collect(Collectors.toList())

        then:
        words == targetTexts
    }

    def "Should collect specified matched group"() {
        given:
        def pattern = Pattern.compile("test(\\d+)")
        def targetTexts = ["test1", "test2", "test3"]
        def nonTargetTexts = ["tes1t", "test"]
        def groupIndex = 1

        when:
        def matchSpliterator = MatchSpliterator.from(pattern, (targetTexts + nonTargetTexts).join(" "), groupIndex)
        def words = matchSpliterator.stream().collect(Collectors.toList())

        then:
        words == ["1", "2", "3"]
    }
}
