package com.hongsi.martholidayalarm.client.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.hongsi.martholidayalarm.constants.CommonConstants;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirebaseAppManager {

	public static final String PROJECT_ID = "mart-holiday-b4599";
	public static final String DATABASE_URL = "https://" + PROJECT_ID + ".firebaseio.com";
	private static final String SERVICE_KEY_FILE =
			CommonConstants.EXTERNAL_CONFIG_FILEPATH + "serviceAccountKey.json";
	private static final List<String> SCOPES = Arrays
			.asList("https://www.googleapis.com/auth/firebase.messaging");

	private FirebaseAppManager() {
	}

	public static FirebaseApp getInstance() {
		return Singleton.instance;
	}

	private static class Singleton {

		private static FirebaseApp instance;

		static {
			try {
				FirebaseOptions options = new FirebaseOptions.Builder()
						.setProjectId(PROJECT_ID)
						.setCredentials(GoogleCredentials
								.fromStream(new FileInputStream(SERVICE_KEY_FILE))
								.createScoped(SCOPES))
						.setDatabaseUrl(DATABASE_URL)
						.build();
				instance = FirebaseApp.initializeApp(options);
			} catch (IOException e) {
				log.error("Initialize error [{}] : can't read {}", e.getMessage(),
						SERVICE_KEY_FILE);
			}
		}
	}
}
