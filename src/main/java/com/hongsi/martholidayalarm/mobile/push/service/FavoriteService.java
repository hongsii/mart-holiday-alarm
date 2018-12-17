package com.hongsi.martholidayalarm.mobile.push.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hongsi.martholidayalarm.mobile.push.domain.User;
import com.hongsi.martholidayalarm.mobile.push.firebase.FirebaseDatabaseManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FavoriteService {

	public static final String NODE_USERS = "users";

	public void removeFavoritedMart(List<Long> deletedIds) {
		List<User> users = getUsers();
		for (User user : users) {
			Iterator<Long> favoriteIterator = user.getFavorites().iterator();
			boolean hasDeletedMart = false;
			while (favoriteIterator.hasNext()) {
				Long martId = favoriteIterator.next();
				if (deletedIds.contains(martId)) {
					hasDeletedMart = true;
					favoriteIterator.remove();
				}
			}

			if (hasDeletedMart) {
				FirebaseDatabaseManager.getReference().child(NODE_USERS)
						.child(user.getDeviceToken())
						.setValueAsync(user);
			}
		}
	}

	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		try {
			CountDownLatch latch = new CountDownLatch(1);
			FirebaseDatabaseManager.getReference().child(NODE_USERS)
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