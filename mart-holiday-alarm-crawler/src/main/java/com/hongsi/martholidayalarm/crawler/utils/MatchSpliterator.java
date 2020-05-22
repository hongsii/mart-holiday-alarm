package com.hongsi.martholidayalarm.crawler.utils;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MatchSpliterator extends AbstractSpliterator<String> {

    private static final int NOT_USE = -1;

    private final Matcher matcher;
    private final int groupIndex;

    public MatchSpliterator(Matcher matcher) {
        this(matcher, NOT_USE);
    }

    public MatchSpliterator(Matcher matcher, int groupIndex) {
        super(matcher.regionEnd() - matcher.regionStart(), ORDERED | IMMUTABLE);
        this.matcher = matcher;
        this.groupIndex = groupIndex;
    }

    public static MatchSpliterator from(Matcher matcher) {
        return new MatchSpliterator(matcher);
    }

    public static MatchSpliterator from(Pattern pattern, String text) {
        return new MatchSpliterator(pattern.matcher(text));
    }

    public static MatchSpliterator from(Pattern pattern, String text, int groupIndex) {
        return new MatchSpliterator(pattern.matcher(text), groupIndex);
    }

    @Override
    public boolean tryAdvance(Consumer<? super String> action) {
        if (!matcher.find()) {
            return false;
        }

        if (groupIndex == NOT_USE) {
            action.accept(matcher.group());
        } else {
            action.accept(matcher.group(groupIndex));
        }
        return true;
    }

    public Stream<String> stream() {
        return StreamSupport.stream(this, false);
    }
}
