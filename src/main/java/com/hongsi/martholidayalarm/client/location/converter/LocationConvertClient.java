package com.hongsi.martholidayalarm.client.location.converter;

import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.crawler.CrawledMart;

public interface LocationConvertClient {

	LocationConvertResult convert(CrawledMart crawledMart);
}
