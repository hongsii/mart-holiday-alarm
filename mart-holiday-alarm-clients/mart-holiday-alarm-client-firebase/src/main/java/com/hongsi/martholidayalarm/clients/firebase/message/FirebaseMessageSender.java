package com.hongsi.martholidayalarm.clients.firebase.message;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.hongsi.martholidayalarm.clients.firebase.exception.PushException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FirebaseMessageSender {

    public String send(Message message) throws PushException {
        String messageId = null;
        try {
            messageId = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("failed to send message. errorCode : {}, message : {}", e.getErrorCode(), e.getMessage());
            throw new PushException(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            log.error("failed to send message.", e);
        }
        return messageId;
    }
}
