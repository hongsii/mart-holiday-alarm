package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.mart.domain.Mart;
import lombok.Getter;
import org.jsoup.nodes.Document;

@Getter
public abstract class MartPage {

	String url;
	Document page;

	public MartPage(String url, Document page) {
		this.url = url;
		this.page = page;
	}

	public abstract Mart getInfo();
}
