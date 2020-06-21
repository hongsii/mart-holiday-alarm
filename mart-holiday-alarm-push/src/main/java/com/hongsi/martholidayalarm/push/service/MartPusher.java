package com.hongsi.martholidayalarm.push.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hongsi.martholidayalarm.push.model.PushUser;
import com.hongsi.martholidayalarm.push.repository.PushUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MartPusher {

    private final PushUserRepository pushUserRepository;
    private final MartPushAsyncService martPushAsyncService;

    public void pushToUsers() {
        pushUserRepository.findAll(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            PushUser pushUser = userSnapshot.getValue(PushUser.class);
                            pushUser.setDeviceToken(userSnapshot.getKey());
                            martPushAsyncService.push(pushUser);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        log.error("failed to get push user. exception: {}, message: {}", error.toException(), error.getMessage());
                    }
                }
        );
    }
}
