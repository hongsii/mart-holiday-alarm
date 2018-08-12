package com.hongsi.martholidayalarm.bot.kakao.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import com.hongsi.martholidayalarm.bot.kakao.domain.Button;
import com.hongsi.martholidayalarm.bot.kakao.domain.UserRequest;
import com.hongsi.martholidayalarm.bot.kakao.dto.BotResponse;
import com.hongsi.martholidayalarm.bot.kakao.repository.KakaoBotRepository;
import com.hongsi.martholidayalarm.common.mart.domain.Mart;
import com.hongsi.martholidayalarm.common.mart.domain.MartType;
import com.hongsi.martholidayalarm.common.mart.repository.MartRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaoBotServiceTest {

	@Autowired
	KakaoBotService kakaoBotService;
	@Autowired
	KakaoBotRepository kakaoBotRepository;
	@Autowired
	MartRepository martRepository;

	@Before
	public void setUp() {
		List<Mart> marts = new ArrayList<>();
		marts.add(Mart.builder()
				.realId("1")
				.martType(MartType.EMART)
				.branchName("서울점")
				.region("서울")
				.build());
		marts.add(Mart.builder()
				.realId("2")
				.martType(MartType.EMART)
				.branchName("강남점")
				.region("서울")
				.build());
		marts.add(Mart.builder()
				.realId("3")
				.martType(MartType.EMART)
				.branchName("경기점")
				.region("경기")
				.build());
		marts.add(Mart.builder()
				.realId("4")
				.martType(MartType.EMART)
				.branchName("경주")
				.region("경상")
				.build());
		marts.add(Mart.builder()
				.realId("1")
				.martType(MartType.LOTTEMART)
				.branchName("부산점")
				.region("부산")
				.build());
		martRepository.saveAll(marts);
	}

	@After
	public void cleanup() {
		kakaoBotRepository.deleteAll();
		martRepository.deleteAll();
	}

	@Test
	public void 최초_사용자가_요청시_버튼생성() {
		UserRequest userRequest = UserRequest.builder()
				.userKey("1234qwer")
				.content("마트 휴일 조회")
				.type("text")
				.build();

		BotResponse botResponse = kakaoBotService.parse(userRequest);
		assertThat("_마트 휴일 조회", is(userRequest.getPath()));
		assertArrayEquals(Arrays.stream(MartType.values()).map(martType -> martType.getName())
						.collect(Collectors.toList()).toArray(),
				botResponse.getKeyboard().getButtons());
	}

	@Test
	public void 사용자가_휴일조회_후_버튼생성() {
		UserRequest branchRequest = UserRequest.builder()
				.userKey("1234qwer")
				.content("서울")
				.button(Button.REGION)
				.path("_마트 휴일 조회_이마트_서울")
				.type("text")
				.build();
		kakaoBotRepository.save(branchRequest);

		UserRequest userRequest = UserRequest.builder()
				.userKey("1234qwer")
				.content("강남점")
				.button(Button.BRANCH)
				.type("text")
				.build();

		BotResponse botResponse = kakaoBotService.parse(userRequest);

		assertThat("_마트 휴일 조회_이마트_서울_강남점", is(userRequest.getPath()));
		assertArrayEquals(new String[]{"마트 휴일 조회"}, botResponse.getKeyboard().getButtons());
	}
}