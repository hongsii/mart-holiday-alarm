package com.hongsi.martholidayalarm.clients.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FirebaseMessageClient {

    public String send(Message message) {
        String messageId = null;
        try {
            messageId = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("[PUSH] can't send message -> errorCode : {}, message : {}", e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("[PUSH] Sender error -> message : {}, cause : {}", e.getMessage(), e.getCause());
        }
        return messageId;
    }
}
