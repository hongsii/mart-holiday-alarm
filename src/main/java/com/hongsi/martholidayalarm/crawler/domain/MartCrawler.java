package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.io.IOException;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class MartCrawler {

	public static MartCrawler create(MartType martType) {
		switch (martType) {
			case EMART:
				return new EmartCrawler();
		}
		return null;
	}

	public abstract List<MartPage> crawl() throws IOException;
}
