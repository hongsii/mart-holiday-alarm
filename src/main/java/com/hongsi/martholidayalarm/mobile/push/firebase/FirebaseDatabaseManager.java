package com.hongsi.martholidayalarm.mobile.push.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDatabaseManager {

	public static final String DEFAULT_NODE = "/";

	public static FirebaseDatabase getInstance() {
		return FirebaseDatabase.getInstance(FirebaseAppManager.getInstance());
	}

	public static DatabaseReference getReference() {
		return getInstance().getReference(DEFAULT_NODE);
	}
}
