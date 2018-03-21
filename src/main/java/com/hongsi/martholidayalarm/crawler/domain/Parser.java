package com.hongsi.martholidayalarm.crawler.domain;

import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.io.IOException;
import java.util.List;

public abstract class Parser {
	public static Parser create(MartType martType) {
		switch (martType) {
			case EMART:
				return new EmartParser();
		}
		return null;
	}

	public abstract List<Mart> getParsedData() throws IOException;
}
