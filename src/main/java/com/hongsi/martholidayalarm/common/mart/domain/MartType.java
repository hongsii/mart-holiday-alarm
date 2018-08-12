package com.hongsi.martholidayalarm.common.mart.domain;

import com.hongsi.martholidayalarm.crawler.domain.Crawlable;
import com.hongsi.martholidayalarm.crawler.domain.EmartCrawler;
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
