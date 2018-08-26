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
import org.assertj.core.api.Assertions;
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
		int i = 1;
		String[] regions = {"서울", "부산", "경기"};
		String[] branchNames = {"강남점", "부산점", "경기점"};
		for (MartType martType : MartType.values()) {
			for (int index = 0; index < regions.length; index++) {
				marts.add(Mart.builder()
						.realId(String.valueOf(i++))
						.martType(martType)
						.region(regions[index])
						.branchName(branchNames[index])
						.build());
			}
		}
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
		Assertions.assertThat(botResponse.getKeyboard().getButtons())
				.containsOnlyElementsOf(Arrays.stream(MartType.values())
						.map(martType -> martType.getName())
						.collect(Collectors.toList()));
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