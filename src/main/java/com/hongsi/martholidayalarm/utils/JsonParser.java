package com.hongsi.martholidayalarm.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongsi.martholidayalarm.exception.CrawlerDataParseException;
import com.hongsi.martholidayalarm.exception.PageNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonParser {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static JsonNode parse(String pageUrl) {
		try {
			URL url = new URL(pageUrl);
			return objectMapper.readTree(url);
		} catch (MalformedURLException e) {
			log.error("[ERROR][CRAWLING][JSON] - Can't find page : {}, message : {}", pageUrl,
					e.getMessage());
			throw new PageNotFoundException();
		} catch (IOException e) {
			log.error("[ERROR][CRAWLING][JSON] - Can't get page : {}, message : {}", pageUrl,
					e.getMessage());
			throw new CrawlerDataParseException();
		}
	}

	public static <T> T parse(String pageUrl, Class<T> type) {
		return objectMapper.convertValue(parse(pageUrl), type);
	}

	public static <T> T convert(JsonNode from, Class<T> type) {
		return objectMapper.convertValue(from, type);
	}

	public static <T> T convert(JsonNode from, TypeReference<?> type) {
		return objectMapper.convertValue(from, type);
	}
}
