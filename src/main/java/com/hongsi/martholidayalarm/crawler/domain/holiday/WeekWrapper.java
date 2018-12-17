package com.hongsi.martholidayalarm.crawler.domain.holiday;

import static java.util.Arrays.asList;

import com.hongsi.martholidayalarm.crawler.utils.MatchIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum WeekWrapper {

	FIRST("첫"), SECOND("둘", "두"), THIRD("셋", "세"), FOURTH("넷", "네"), FIFTH("다섯");

	private static final String REGEX_DELIMITER = "|";
	private static final Pattern MATCH_CHARACTER_PATTERN = Pattern.compile(
			Arrays.stream(values())
					.flatMap(weekWrapper -> weekWrapper.getMatchCharacters().stream())
					.collect(Collectors.joining(REGEX_DELIMITER))
	);

	private List<String> matchCharacters = new ArrayList<>();

	WeekWrapper(String... matchCharacter) {
		matchCharacters.addAll(asList(matchCharacter));
		matchCharacters.add(Integer.toString(getWeek()));
	}

	public static WeekWrapper of(String text) {
		return Arrays.stream(values())
				.filter(weekWrapper -> weekWrapper.hasCharacter(text))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						"Not found week of this text : " + text));
	}

	public static List<WeekWrapper> parseToCollection(String text) {
		Matcher matcher = MATCH_CHARACTER_PATTERN.matcher(text);
		return MatchIterator.from(matcher).stream()
				.map(WeekWrapper::of)
				.collect(Collectors.toList());
	}

	private List<String> getMatchCharacters() {
		return matchCharacters;
	}

	public boolean hasCharacter(String text) {
		return matchCharacters.contains(text);
	}

	public int getWeek() {
		return ordinal() + 1;
	}
}
