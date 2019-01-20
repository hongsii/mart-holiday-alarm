package com.hongsi.martholidayalarm.exception;

public class CrawlerNotFoundException extends Exception {

	public CrawlerNotFoundException() {
		this("해당 마트의 크롤러가 존재하지 않습니다.");
	}

	public CrawlerNotFoundException(String message) {
		super(message);
	}
}
