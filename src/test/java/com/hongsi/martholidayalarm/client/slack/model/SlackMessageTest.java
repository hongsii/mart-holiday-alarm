package com.hongsi.martholidayalarm.client.slack.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class SlackMessageTest {

    @Autowired
    private JacksonTester<SlackMessage> json;

    @Test
    public void test() throws Exception {
        SlackMessage slackMessage = SlackMessage.builder()
                .username("장애 알림")
                .iconEmoji(Emoji.FIRE)
                .channel(SlackChannel.SERVER_ERROR)
                .attachments(Arrays.asList(
                        SlackMessage.Attachment.builder()
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

        String expected = "{ \"username\": \"장애 알림\", \"icon_emoji\": \":FIRE:\", \"channel\": \"CN4LW9R16\", \"attachments\": [ { \"color\": \"#FF0000\", \"fields\": [ { \"title\": \"Error\", \"value\": \"NotFoundException\", \"short\": true }, { \"title\": \"StackTrace\", \"value\": \"Stack\", \"short\": false } ] } ] }\n";
        assertThat(slackMessage).isEqualTo(json.parse(expected));
    }
}