package com.hongsi.martholidayalarm.domain.crawler;

import com.hongsi.martholidayalarm.client.location.converter.LocationConvertClient;
import com.hongsi.martholidayalarm.client.location.converter.dto.LocationConvertResult;
import com.hongsi.martholidayalarm.domain.mart.Crawlable;

public abstract class AbstractMartCrawler implements MartCrawler {

	private final LocationConvertClient locationConvertClient;

	protected AbstractMartCrawler(LocationConvertClient locationConvertClient) {
		this.locationConvertClient = locationConvertClient;
	}

	public CrawlerMartData toCrawlerMartData(Crawlable crawlable) {
		CrawlerMartData crawlerMartData = crawlable.toData();
		return setAddressInfoFromExternalApi(crawlerMartData);
	}

	private CrawlerMartData setAddressInfoFromExternalApi(CrawlerMartData crawlerMartData) {
		LocationConvertResult locationConvertResult = locationConvertClient.convert(crawlerMartData);
		crawlerMartData.setAddressInfo(locationConvertResult);
		return crawlerMartData;
	}
}
