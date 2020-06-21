package com.hongsi.martholidayalarm.push.service;

import com.hongsi.martholidayalarm.clients.firebase.exception.PushException;
import com.hongsi.martholidayalarm.clients.firebase.message.FirebaseMessageSender;
import com.hongsi.martholidayalarm.push.model.MartPushMessage;
import com.hongsi.martholidayalarm.push.model.PushMart;
import com.hongsi.martholidayalarm.push.model.PushUser;
import com.hongsi.martholidayalarm.push.repository.PushMartRepository;
import com.hongsi.martholidayalarm.push.repository.PushUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class MartPushAsyncService {

    private final PushMartRepository pushMartRepository;
    private final PushUserRepository pushUserRepository;
    private final FirebaseMessageSender sender;

    @Async("pushThreadPool")
    public void push(PushUser pushUser) {
        List<PushMart> pushMarts = pushMartRepository.findAllByIdInAndHolidayDate(pushUser.getFavorites(), getTomorrowDate());
        String deviceToken = pushUser.getDeviceToken();
        pushMarts.stream()
                .parallel()
                .map(pushMart -> MartPushMessage.of(deviceToken, pushMart))
                .peek(it -> log.info("push to user. message: {}", it))
                .forEach(send(deviceToken));
    }

    private LocalDate getTomorrowDate() {
        return LocalDate.now().plusDays(1);
    }

    private Consumer<MartPushMessage> send(String deviceToken) {
        return martPushMessage -> {
            try {
                sender.send(martPushMessage.toFirebaseMessage());
            } catch (PushException e) {
                if (e.isDeletedToken()) {
                    pushUserRepository.delete(deviceToken);
                }
                throw e;
            }
        };
    }
}
