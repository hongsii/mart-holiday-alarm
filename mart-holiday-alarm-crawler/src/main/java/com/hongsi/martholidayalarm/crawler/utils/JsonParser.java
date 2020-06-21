package com.hongsi.martholidayalarm.crawler.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongsi.martholidayalarm.crawler.exception.CrawlerDataParseException;
import com.hongsi.martholidayalarm.crawler.exception.PageNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class JsonParser {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T request(String pageUrl, Class<T> type) {
		return objectMapper.convertValue(request(pageUrl), type);
	}

	public static JsonNode request(String pageUrl) {
		try {
			URL url = new URL(pageUrl);
			return objectMapper.readTree(url);
		} catch (MalformedURLException e) {
			log.error("can't find page : {}, message : {}", pageUrl,
					e.getMessage());
			throw new PageNotFoundException();
		} catch (IOException e) {
			log.error("can't get page : {}, message : {}", pageUrl,
					e.getMessage());
			throw new CrawlerDataParseException();
		}
	}

	public static <T> T convert(JsonNode from, Class<T> type) {
		return objectMapper.convertValue(from, type);
	}

	public static <T> T convert(JsonNode from, TypeReference<T> type) {
		return objectMapper.convertValue(from, type);
	}
}
