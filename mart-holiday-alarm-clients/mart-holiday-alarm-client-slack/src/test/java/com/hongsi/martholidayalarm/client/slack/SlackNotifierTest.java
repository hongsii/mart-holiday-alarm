package com.hongsi.martholidayalarm.client.slack;

import com.hongsi.martholidayalarm.client.slack.model.Color;
import com.hongsi.martholidayalarm.client.slack.model.Emoji;
import com.hongsi.martholidayalarm.client.slack.model.SlackChannel;
import com.hongsi.martholidayalarm.client.slack.model.SlackMessage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class SlackNotifierTest {

    private final SlackNotifier slackNotifier = new SlackNotifier(new RestTemplate());

    @Disabled
    @Test
    public void testNotify() {
        SlackMessage slack = SlackMessage.builder()
                .username("장애 알림")
                .iconEmoji(Emoji.FIRE)
                .channel(SlackChannel.CRAWLING_ALARM)
                .attachments(Collections.singletonList(
                        SlackMessage.Attachment.builder()
                                .text("이마트 크롤링 실패")
                                .color(Color.RED)
                                .fields(Arrays.asList(
                                        SlackMessage.Attachment.Field.builder()
                                                .title("Error")
                                                .value("NotFoundException")
                                                .shortField(true)
                                                .build(),
                                        SlackMessage.Attachment.Field.builder()
                                                .title("StackTrace")
                                                .value("Stack")
                                                .shortField(false)
                                                .build()
                                ))
                                .build()
                ))
                .build();

        boolean success = slackNotifier.notify(slack);

        assertThat(success).isTrue();
    }
}