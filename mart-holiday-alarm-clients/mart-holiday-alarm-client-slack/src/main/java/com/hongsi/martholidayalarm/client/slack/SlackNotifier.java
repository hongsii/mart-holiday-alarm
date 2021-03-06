package com.hongsi.martholidayalarm.client.slack;

import com.hongsi.martholidayalarm.client.slack.model.SlackMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackNotifier {

    private final RestTemplate restTemplate;

    public SlackNotifier() {
        this.restTemplate = new RestTemplate();
    }

    @Value("${slack.webhook.url:''}")
    private String url;

    public boolean notify(SlackMessage slackMessage) {
        try {
            restTemplate.postForEntity(url, slackMessage, String.class);
            return true;
        } catch (Exception e) {
            log.error("failed to notify slack message", e);
            return false;
        }
    }
}
