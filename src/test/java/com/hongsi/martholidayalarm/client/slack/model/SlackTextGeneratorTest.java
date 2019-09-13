package com.hongsi.martholidayalarm.client.slack.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SlackTextGeneratorTest {

    @Test
    public void testNewLine() {
        assertThat(SlackTextGenerator.newLine("test")).isEqualTo("test\n");
    }

    @Test
    public void testCodeBlock() {
        assertThat(SlackTextGenerator.codeBlock("test")).isEqualTo("```test```");
    }
}