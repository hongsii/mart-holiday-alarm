package com.hongsi.martholidayalarm.crawler.exception;

public class CrawlDataParseException extends RuntimeException {

	public CrawlDataParseException() {
		super("데이터를 파싱할 수 없습니다.");
	}

	public CrawlDataParseException(String message) {
		super(message);
	}
}
