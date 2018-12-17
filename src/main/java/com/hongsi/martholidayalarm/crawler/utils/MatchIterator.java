package com.hongsi.martholidayalarm.crawler.utils;

import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MatchIterator extends AbstractSpliterator<String> {

	private final Matcher matcher;

	public MatchIterator(Matcher matcher) {
		super(matcher.regionEnd() - matcher.regionStart(), ORDERED | IMMUTABLE);
		this.matcher = matcher;
	}

	public static MatchIterator from(Matcher matcher) {
		return new MatchIterator(matcher);
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
