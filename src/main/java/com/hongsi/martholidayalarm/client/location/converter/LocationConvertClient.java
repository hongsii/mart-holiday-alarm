package com.hongsi.martholidayalarm.client.location.converter;

import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawledMart;

public interface LocationConvertClient {

	LocationConvertResult convert(CrawledMart crawledMart);
}
