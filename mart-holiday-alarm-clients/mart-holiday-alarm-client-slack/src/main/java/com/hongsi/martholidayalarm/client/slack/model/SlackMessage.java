package com.hongsi.martholidayalarm.client.slack.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SlackMessage {

    private String username;
    private Emoji iconEmoji;
    private SlackChannel channel;
    private String text;
    private List<Attachment> attachments;

    public static SlackMessage success(String title, Attachment... attachments) {
        return SlackMessage.builder()
                .username(title)
                .text(title)
                .iconEmoji(Emoji.SUNNY)
                .channel(SlackChannel.CRAWLING_ALARM)
                .attachments(Arrays.asList(attachments))
                .build();
    }

    public static SlackMessage error(Attachment... attachments) {
        return SlackMessage.builder()
                .username("장애 알림")
                .iconEmoji(Emoji.FIRE)
                .channel(SlackChannel.CRAWLING_ALARM)
                .attachments(Arrays.asList(attachments))
                .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class Attachment {

        private String text;
        private Color color;
        private List<Field> fields;

        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
        public static class Field {

            private String title;
            private String value;
            @JsonProperty("short")
            private boolean shortField;
        }
    }
}
