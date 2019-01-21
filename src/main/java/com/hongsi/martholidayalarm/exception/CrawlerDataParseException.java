package com.hongsi.martholidayalarm.exception;

public class CrawlerDataParseException extends RuntimeException {

	public CrawlerDataParseException() {
		super("데이터를 파싱할 수 없습니다.");
	}

	public CrawlerDataParseException(String message) {
		super(message);
	}
}
