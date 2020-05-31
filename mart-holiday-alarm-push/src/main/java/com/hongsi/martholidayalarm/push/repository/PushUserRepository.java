package com.hongsi.martholidayalarm.push.repository;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.hongsi.martholidayalarm.clients.firebase.database.domain.FirebaseDatabaseWrapper.root;

@Repository
@Slf4j
public class PushUserRepository {

    private static final String USERS = "users";

    public void findAll(ValueEventListener callback) {
        users().addListenerForSingleValueEvent(callback);
    }

    public void delete(String deviceToken) {
        users().child(deviceToken).removeValue((error, ref) -> {
            if (error == null) {
                log.info("successfully removed device token: {}", ref.getKey());
            } else {
                log.error("failed to remove device token: {}, error: {}", ref.getKey(), error.getMessage());
            }
        });
    }

    private DatabaseReference users() {
        return root().child(USERS);
    }
}
