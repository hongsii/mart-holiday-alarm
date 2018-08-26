package com.hongsi.martholidayalarm.crawler.exception;

public class PageNotFoundException extends Exception {

	public PageNotFoundException() {
		super("존재하지 않는 사이트이거나, 사이트 내 정보가 존재하지 않습니다.");
	}

	public PageNotFoundException(String message) {
		super(message);
	}
}
