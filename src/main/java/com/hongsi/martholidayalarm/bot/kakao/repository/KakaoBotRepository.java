package com.hongsi.martholidayalarm.bot.kakao.repository;

import com.hongsi.martholidayalarm.bot.kakao.domain.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoBotRepository extends JpaRepository<UserRequest, Long> {

	UserRequest findByUserKey(String userKey);

	void deleteByUserKey(String userKey);
}
