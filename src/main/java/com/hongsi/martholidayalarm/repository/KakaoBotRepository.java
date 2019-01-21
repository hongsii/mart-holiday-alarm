package com.hongsi.martholidayalarm.repository;

import com.hongsi.martholidayalarm.domain.bot.kakao.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoBotRepository extends JpaRepository<UserRequest, Long> {

	UserRequest findByUserKey(String userKey);

	void deleteByUserKey(String userKey);
}
