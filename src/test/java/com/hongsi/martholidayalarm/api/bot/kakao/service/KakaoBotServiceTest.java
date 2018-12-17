package com.hongsi.martholidayalarm.api.bot.kakao.service;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

import com.hongsi.martholidayalarm.api.bot.kakao.domain.Button;
import com.hongsi.martholidayalarm.api.bot.kakao.domain.UserRequest;
import com.hongsi.martholidayalarm.api.bot.kakao.dto.BotResponse;
import com.hongsi.martholidayalarm.api.bot.kakao.repository.KakaoBotRepository;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.repository.MartRepository;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
		String[] regions = {"서울", "부산", "경기"};
		String[] branchNames = {"강남점", "부산점", "경기점"};
		martRepository.saveAll(IntStream.range(0, regions.length)
				.mapToObj(index -> Mart.builder()
						.realId(String.valueOf(index + 1))
						.martType(MartType.EMART)
						.region(regions[index])
						.branchName(branchNames[index])
						.build())
				.collect(Collectors.toList()));
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
				.containsOnlyElementsOf(asList(MartType.EMART.getDisplayName()));
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