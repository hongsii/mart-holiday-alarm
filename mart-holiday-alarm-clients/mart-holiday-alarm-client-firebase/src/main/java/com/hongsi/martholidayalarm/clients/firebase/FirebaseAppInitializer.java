package com.hongsi.martholidayalarm.clients.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseAppInitializer {

    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/firebase.messaging");

    @Value("${firebase.project-id}")
    private String projectId;
    @Value("${firebase.service-key-file}")
    private String serviceKeyFile;

    @PostConstruct
    public void initialize() throws Exception {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials
                            .fromStream(new FileInputStream(serviceKeyFile))
                            .createScoped(SCOPES))
                    .setDatabaseUrl("https://" + projectId + ".firebaseio.com")
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                log.info("Initialize firebase app! projectId: {}, serviceKeyFile: {}", projectId, serviceKeyFile);
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            log.error("Failed to initialize firebase app", e);
            throw e;
        }
    }
}
