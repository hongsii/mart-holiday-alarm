package com.hongsi.martholidayalarm.client.location.converter;

import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.crawler.CrawlerMartData;

public interface LocationConvertClient {

	LocationConvertResult convert(CrawlerMartData crawlerMartData);
}
