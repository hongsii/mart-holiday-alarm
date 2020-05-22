package com.hongsi.martholidayalarm.crawler.model.holiday;

import com.hongsi.martholidayalarm.crawler.utils.MatchSpliterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public enum KoreanWeek {

    FIRST("첫"), SECOND("둘", "두"), THIRD("셋", "세"), FOURTH("넷", "네"), FIFTH("다섯");

    private static final String REGEX_DELIMITER = "|";
    private static final Pattern WEEK_PATTERN = Pattern.compile(
            Arrays.stream(KoreanWeek.values())
                    .flatMap(koreanWeek -> koreanWeek.characters.stream())
                    .collect(Collectors.joining(REGEX_DELIMITER))
    );

    private final List<String> characters;

    KoreanWeek(String... characters) {
        this.characters = new ArrayList<>(characters.length + 1);
        this.characters.addAll(Arrays.asList(characters));
        this.characters.add(Integer.toString(getWeek()));
    }

    public static KoreanWeek of(String text) {
        return Arrays.stream(values())
                .filter(weekWrapper -> weekWrapper.hasCharacter(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not found week of this text : " + text));
    }

    public static List<KoreanWeek> parseToCollection(String text) {
        Matcher matcher = WEEK_PATTERN.matcher(text);
        return MatchSpliterator.from(matcher).stream()
                .map(KoreanWeek::of)
                .collect(Collectors.toList());
    }

    public int getWeek() {
        return ordinal() + 1;
    }

    private boolean hasCharacter(String text) {
        return characters.contains(text);
    }
}
