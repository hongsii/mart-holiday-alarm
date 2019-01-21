package com.hongsi.martholidayalarm.client.location.converter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.annotation.ExternalJsonItem;
import com.hongsi.martholidayalarm.exception.CannotConvertLocation;
import java.util.List;

@ExternalJsonItem
public class KakaoLocationSearchResult {

	@JsonProperty
	private List<KakaoLocationSearchItem> documents;

	public LocationConvertResult findLocationConvertResult() {
		return documents.stream()
				.findFirst()
				.map(item -> new LocationConvertResult(item.getAddress(), item.getLocation()))
				.orElseThrow(CannotConvertLocation::new);
	}
}
