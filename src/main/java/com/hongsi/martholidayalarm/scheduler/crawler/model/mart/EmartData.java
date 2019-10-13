package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.domain.mart.Holiday;
import com.hongsi.martholidayalarm.domain.mart.Location;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import com.hongsi.martholidayalarm.scheduler.crawler.model.CrawlerMartType;
import com.hongsi.martholidayalarm.utils.RegionParser;
import com.hongsi.martholidayalarm.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmartData implements Crawlable {

	private static final String VIEW_URL_SUFFIX = "/branch/list.do?id=";

	@JsonProperty("ID")
	private String realId;

	@JsonProperty("NAME")
	private String branchName;

	@JsonProperty("AREA_CODE")
	private String regionCode;

	@JsonProperty("TEL")
	private String phoneNumber;

	@JsonProperty("ADDRESS1")
	private String roadNameAddress;

	@JsonProperty("ADDRESS3")
	private String oldAddress;

	@JsonProperty("STORE_SHOPPING_TIME")
	private String openingHours;

	private StoreType storeType;

	@JsonProperty("MAP_X")
	private String rawLatitude;

	@JsonProperty("MAP_Y")
	private String rawLongitude;

	@JsonIgnore
	private Location location;

	@JsonIgnore
	private List<Holiday> holidays;

	@JsonProperty("STORE_TP")
	private void setStoreType(String storeType) {
		this.storeType = StoreType.of(storeType);
	}

	@Override
	public MartType getMartType() {
		return storeType.getMartType();
	}

	@Override
	public String getRealId() {
		return realId;
	}

	@Override
	public String getBranchName() {
	    return storeType.removePrefix(branchName);
	}

	@Override
	public String getRegion() {
		return RegionParser.getRegionFromAddress(getAddress());
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public String getAddress() {
		if (ValidationUtils.isNotBlank(roadNameAddress)) {
			return roadNameAddress;
		}
		return oldAddress;
	}

	@Override
	public String getOpeningHours() {
		return openingHours;
	}

	@Override
	public String getUrl() {
		String suffix = VIEW_URL_SUFFIX + getRealId();
		return CrawlerMartType.EMART.appendUrl(suffix);
	}

	@Override
	public Location getLocation() {
		if (Objects.isNull(location)) {
			location = Location.parse(rawLatitude, rawLongitude);
		}
		return location;
	}

	@Override
	public String getHolidayText() {
		return ""; // 이마트는 휴무일 텍스트가 없음
	}

	@Override
	public List<Holiday> getHolidays() {
	    return holidays;
	}

	void addHolidays(List<EmartHolidayData> holidayData) {
		holidays = holidayData.stream()
				.filter(data -> data.isSameId(realId))
				.flatMap(data -> data.getHolidays().stream())
				.filter(Holiday::isUpcoming)
				.sorted()
				.collect(toList());
	}

	@AllArgsConstructor
	public enum StoreType {
		EMART(MartType.EMART, Arrays.asList("E", "A")),
		TRADERS(MartType.EMART_TRADERS, "T", "트레이더스"),
		NOBRAND(MartType.NOBRAND, "S", "노브랜드"),
		UNKNOWN(null, "");

		private MartType martType;
		private List<String> typeCharacters;
		private String prefix;

		StoreType(MartType martType, String typeCharacter) {
		    this(martType, Collections.singletonList(typeCharacter));
		}

		StoreType(MartType martType, String typeCharacter, String prefix) {
			this(martType, Collections.singletonList(typeCharacter), prefix);
		}

		StoreType(MartType martType, List<String> typeCharacters) {
			this(martType, typeCharacters, "");
		}

		public static StoreType of(String typeCharacter) {
			return Arrays.stream(values())
					.filter(storeType -> storeType.hasCharacter(typeCharacter))
					.findFirst()
					.orElse(UNKNOWN);
		}

		private boolean hasCharacter(String character) {
			return typeCharacters.contains(character);
		}

		public MartType getMartType() {
			return martType;
		}

		public String removePrefix(String branchName) {
			if (prefix.isEmpty()) return branchName;

			return branchName.replaceAll(prefix + "\\s+", "");
		}
	}

	@Override
	public String toString() {
		return "EmartData{" +
				"realId='" + realId + '\'' +
				", branchName='" + branchName + '\'' +
				", regionCode='" + regionCode + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", roadNameAddress='" + roadNameAddress + '\'' +
				", oldAddress='" + oldAddress + '\'' +
				", openingHours='" + openingHours + '\'' +
				", storeType='" + storeType + '\'' +
				", rawLatitude='" + rawLatitude + '\'' +
				", rawLongitude='" + rawLongitude + '\'' +
				", location=" + location +
				", holidays=" + holidays +
				'}';
	}
}
