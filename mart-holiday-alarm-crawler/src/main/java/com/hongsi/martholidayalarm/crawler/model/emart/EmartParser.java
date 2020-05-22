package com.hongsi.martholidayalarm.crawler.model.emart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.location.Location;
import com.hongsi.martholidayalarm.core.mart.MartType;
import com.hongsi.martholidayalarm.crawler.MartCrawlerType;
import com.hongsi.martholidayalarm.crawler.model.MartParser;
import com.hongsi.martholidayalarm.crawler.utils.RegionParser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmartParser implements MartParser {

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

    @JsonProperty("MAP_X")
    private String rawLatitude;

    @JsonProperty("MAP_Y")
    private String rawLongitude;

    @JsonIgnore
    private List<Holiday> holidays;

    private StoreType storeType;

    @JsonProperty("STORE_TP")
    private void setStoreType(String storeType) {
        this.storeType = StoreType.of(storeType);
    }

    @Override
    public MartType getMartType() {
        return storeType.martType;
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
        return StringUtils.hasText(roadNameAddress) ? roadNameAddress : oldAddress;
    }

    @Override
    public String getOpeningHours() {
        return openingHours;
    }

    @Override
    public String getUrl() {
        String suffix = VIEW_URL_SUFFIX + getRealId();
        return MartCrawlerType.EMART.appendUrl(suffix);
    }

    @Override
    public Location getLocation() {
        return Location.parse(rawLatitude, rawLongitude);
    }

    @Override
    public String getHolidayText() {
        return "";
    }

    @Override
    public List<Holiday> getHolidays() {
        return holidays;
    }

    void addHolidays(List<EmartHolidayParser> holidayData) {
        holidays = holidayData.stream()
                .filter(data -> data.isSameId(realId))
                .flatMap(data -> data.getHolidays().stream())
                .filter(Holiday::isUpcoming)
                .sorted()
                .collect(toList());
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum StoreType {
        EMART(MartType.EMART, Arrays.asList("E", "A")),
        TRADERS(MartType.EMART_TRADERS, Collections.singletonList("T"), "트레이더스"),
        NOBRAND(MartType.NOBRAND, Collections.singletonList("S"), "노브랜드"),
        UNKNOWN;

        private final MartType martType;
        private final List<String> typeCharacters;
        private final String prefixName;

        StoreType() {
            this(null, emptyList());
        }

        StoreType(MartType martType, List<String> typeCharacters) {
            this(martType, typeCharacters, "");
        }

        public static StoreType of(String typeCharacter) {
            return Arrays.stream(values())
                    .filter(storeType -> storeType.typeCharacters.contains(typeCharacter))
                    .findFirst()
                    .orElse(UNKNOWN);
        }

        public String removePrefix(String branchName) {
            return prefixName.isEmpty() ? branchName : branchName.replaceAll(prefixName + "\\s+", "");
        }
    }
}
