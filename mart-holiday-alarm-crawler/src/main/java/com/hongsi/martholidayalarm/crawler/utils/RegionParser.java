package com.hongsi.martholidayalarm.crawler.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegionParser {

    private static final Pattern REPLACING_REGION_PATTERN = Pattern.compile("(경상|충청|전라)(남도|북도)");

    public static String getRegionFromAddress(String address) {
        String region = address.substring(0, 2);
        Matcher matcher = REPLACING_REGION_PATTERN.matcher(address);
        if (matcher.find()) {
            region = matcher.group(1).substring(0, 1) + matcher.group(2).substring(0, 1);
        }
        return region;
    }
}
