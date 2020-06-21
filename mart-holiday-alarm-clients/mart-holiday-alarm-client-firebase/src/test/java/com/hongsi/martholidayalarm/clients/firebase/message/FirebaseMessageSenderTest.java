package com.hongsi.martholidayalarm.clients.firebase.message;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.hongsi.martholidayalarm.clients.firebase.exception.PushException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled("local only")
@SpringBootTest(properties = "spring.config.location=file:${HOME}/app/mart-holiday-alarm/conf/local-application.yml")
class FirebaseMessageSenderTest {

    @Autowired
    private FirebaseMessageSender firebaseMessageSender;

    @Test
    @DisplayName("등록된 토큰으로 푸시 전송")
    void sendByToken() {
        // given
        String deviceToken = "doCqjq3EsWQ:APA91bGiD_YtXdDFCauOAKGzM65nHhlIubjqd9sHQer9FpD4LJLI5y2gEFsVdIe_ldq1p2NPQfpTOswAmFPkf8iVQnVzY00QdSNITkDuyENBM3GDH5GSvqGNVaBZVgexlCkv424D1TQJ";
        Message message = Message.builder()
                .setToken(deviceToken)
                .setNotification(new Notification("테스트", "테스트내용"))
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setSound("default")
                                .setThreadId("martholidayapp")
                                .build())
                        .build())
                .build();

        // when
        String messageId = firebaseMessageSender.send(message);

        // then
        assertThat(messageId).isNotBlank();
    }

    @Test
    @DisplayName("삭제된 토큰에 푸시를 보내면 실패")
    void sendByDeletedToken() {
        // given
        String deviceToken = "cSE1EGF8Lxs:APA91bFoaZVweP4OKXq9S1DoZG_pTp5lM910NKbzQryMirqNEhXG8tVjZNXM8jzqw4tPW8Re20sRgUPmlAoNk_fW8xZfz-uQTPf5YigyDZiLmGhV7fag3ALvynLWDh4U4IapZaE-DBRJ";
        Message message = Message.builder()
                .setToken(deviceToken)
                .setNotification(new Notification("테스트", "테스트내용"))
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setSound("default")
                                .setThreadId("martholidayapp")
                                .build())
                        .build())
                .build();

        // expect
        assertThatThrownBy(() -> firebaseMessageSender.send(message))
                .isInstanceOf(PushException.class)
                .hasMessage("Requested entity was not found.")
                .hasFieldOrPropertyWithValue("errorCode", PushErrorCode.DELETED_TOKEN);
    }
}
