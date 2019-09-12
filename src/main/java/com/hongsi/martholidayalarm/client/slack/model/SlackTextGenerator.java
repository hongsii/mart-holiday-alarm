package com.hongsi.martholidayalarm.client.slack.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SlackTextGenerator {

    private static final String AFFIX_CODE_BLOCK = "```";
    private static final String NEW_LINE = "\n";

    public static String newLine(String text) {
        return text + NEW_LINE;
    }

    public static String codeBlock(String text) {
        return AFFIX_CODE_BLOCK + text + AFFIX_CODE_BLOCK;
    }
}
