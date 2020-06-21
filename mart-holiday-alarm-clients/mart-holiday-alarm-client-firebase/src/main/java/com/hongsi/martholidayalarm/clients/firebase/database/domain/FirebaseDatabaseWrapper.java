package com.hongsi.martholidayalarm.clients.firebase.database.domain;

import com.google.firebase.database.DatabaseReference;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class FirebaseDatabaseWrapper {

    public static final String DEFAULT_NODE = "/";

    public static DatabaseReference root() {
        return getInstance().getReference(DEFAULT_NODE);
    }
}
