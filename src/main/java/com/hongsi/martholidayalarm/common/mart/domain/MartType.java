package com.hongsi.martholidayalarm.common.mart.domain;

import com.hongsi.martholidayalarm.crawler.domain.Crawlable;
import com.hongsi.martholidayalarm.crawler.domain.EmartCrawler;
import com.hongsi.martholidayalarm.crawler.domain.HomeplusCrawler;
import com.hongsi.martholidayalarm.crawler.domain.HomeplusExpressCrawler;
import com.hongsi.martholidayalarm.crawler.domain.LottemartCrawler;
import com.hongsi.martholidayalarm.crawler.domain.MartCrawler;
import lombok.Getter;

@Getter
public enum MartType implements Crawlable {
	EMART("이마트") {
		@Override
		public MartCrawler getMartCrawler() {
			return new EmartCrawler();
		}
	},
	LOTTEMART("롯데마트") {
		@Override
		public MartCrawler getMartCrawler() {
			return new LottemartCrawler();
		}
	},
	HOMEPLUS("홈플러스") {
		@Override
		public MartCrawler getMartCrawler() {
			return new HomeplusCrawler();
		}
	},
	HOMEPLUS_EXPRESS("홈플러스 익스프레스") {
		@Override
		public MartCrawler getMartCrawler() {
			return new HomeplusExpressCrawler();
		}
	};



	private String name;

	MartType(String name) {
		this.name = name;
	}

	public static MartType of(String name) {
		for (MartType martType : MartType.values()) {
			if (martType.name.equals(name)) {
				return martType;
			}
		}
		throw new IllegalArgumentException("해당 마트 타입이 존재하지 않습니다.");
	}

	public static MartType typeOf(String martType) {
		if (martType == null) {
			throw new NullPointerException("MartType is null");
		}
		return Enum.valueOf(MartType.class, martType.trim().toUpperCase());
	}
}
