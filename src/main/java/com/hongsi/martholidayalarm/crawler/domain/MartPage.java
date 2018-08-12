package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import java.io.IOException;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Getter
public abstract class MartPage {

	String url;
	Document page;

	MartPage(String url) throws IOException {
		this.url = url;
		page = Jsoup.connect(url).get();
	}

	public abstract Mart getInfo();
}
