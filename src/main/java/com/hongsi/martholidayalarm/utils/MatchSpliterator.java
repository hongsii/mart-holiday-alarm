package com.hongsi.martholidayalarm.utils;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MatchSpliterator extends AbstractSpliterator<String> {

	private final Matcher matcher;

	public MatchSpliterator(Matcher matcher) {
		super(matcher.regionEnd() - matcher.regionStart(), ORDERED | IMMUTABLE);
		this.matcher = matcher;
	}

	public static MatchSpliterator from(Matcher matcher) {
		return new MatchSpliterator(matcher);
	}

	@Override
	public boolean tryAdvance(Consumer<? super String> action) {
		if (!matcher.find()) {
			return false;
		}
		action.accept(matcher.group());
		return true;
	}

	public Stream<String> stream() {
		return StreamSupport.stream(this, false);
	}
}
