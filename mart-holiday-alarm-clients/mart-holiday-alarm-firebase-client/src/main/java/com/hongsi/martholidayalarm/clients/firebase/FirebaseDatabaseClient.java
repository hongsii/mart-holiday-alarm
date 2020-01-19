package com.hongsi.martholidayalarm.clients.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Component;

@Component
public class FirebaseDatabaseClient {

	public static final String DEFAULT_NODE = "/";

	public static FirebaseDatabase getInstance() {
		return FirebaseDatabase.getInstance();
	}

	public static DatabaseReference getReference() {
		return getInstance().getReference(DEFAULT_NODE);
	}
}
