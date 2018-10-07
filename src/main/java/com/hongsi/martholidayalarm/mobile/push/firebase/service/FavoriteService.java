package com.hongsi.martholidayalarm.mobile.push.firebase.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hongsi.martholidayalarm.mobile.push.firebase.FirebaseDatabaseManager;
import com.hongsi.martholidayalarm.mobile.push.firebase.FirebaseMessageTopicManager;
import com.hongsi.martholidayalarm.mobile.push.firebase.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FavoriteService {

	public static final String NODE_NAME = "favorites";

	public Set<Long> getFavoritedMartIds() {
		Set<Long> martIds = new HashSet<>();
		List<User> users = getUsers();
		for (User user : users) {
			martIds.addAll(user.getFavorites());
		}
		return martIds;
	}

	public void removeFavoritedMart(List<Long> deletedIds) {
		List<User> users = getUsers();
		Map<String, List<String>> topics = new HashMap<>();
		for (User user : users) {
			Iterator<Long> favoriteIterator = user.getFavorites().iterator();
			while (favoriteIterator.hasNext()) {
				Long martId = favoriteIterator.next();
				if (deletedIds.contains(martId)) {
					List<String> tokensToBeUnscribed = topics
							.getOrDefault(martId.toString(), new ArrayList<>());
					tokensToBeUnscribed.add(user.getDeviceToken());

					topics.put(martId.toString(), tokensToBeUnscribed);

					favoriteIterator.remove();
				}
			}
			FirebaseDatabaseManager.getReference().child("users").child(user.getDeviceToken())
					.setValueAsync(user);
		}

		try {
			FirebaseMessageTopicManager.unsubscribe(topics);
		} catch (InterruptedException | ExecutionException e) {
			log.error("can't unsubscribe - message : {}", e.getMessage());
		}
	}

	private List<User> getUsers() {
		List<User> users = new ArrayList<>();
		try {
			CountDownLatch latch = new CountDownLatch(1);
			FirebaseDatabaseManager.getReference().child("users")
					.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot dataSnapshot) {
							for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
								User user = snapshot.getValue(User.class);
								user.setDeviceToken(snapshot.getKey());
								users.add(user);
							}

							latch.countDown();
						}

						@Override
						public void onCancelled(DatabaseError databaseError) {
							log.error("Database read exception - exception : {}, message : {}",
									databaseError.toException(), databaseError.getMessage());
							latch.countDown();
						}
					});
			latch.await();
		} catch (InterruptedException e) {
			log.error("Latch error - message : {}", e.getMessage());
		}
		return users;
	}
}