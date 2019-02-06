package com.hongsi.martholidayalarm.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.Test;

public class MatchSpliteratorTest {

	@Test
	public void matchGroup() {
		MatchSpliterator matchSpliterator = MatchSpliterator
				.from(Pattern.compile("test(\\d+)"), "test1 test2 test3 tes1t");

		List<String> words = matchSpliterator.stream().collect(Collectors.toList());

		assertThat(words).hasSize(3).containsExactly("test1", "test2", "test3");
	}

	@Test
	public void matchSpecifiedGroup() {
		MatchSpliterator matchSpliterator = MatchSpliterator
				.from(Pattern.compile("test(\\d+)"), "test1 test2 test3 tes1t", 1);

		List<String> words = matchSpliterator.stream().collect(Collectors.toList());

		assertThat(words).hasSize(3).containsExactly("1", "2", "3");
	}
}