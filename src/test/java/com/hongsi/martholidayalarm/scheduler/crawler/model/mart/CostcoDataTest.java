package com.hongsi.martholidayalarm.scheduler.crawler.model.mart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongsi.martholidayalarm.domain.mart.Holiday;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CostcoDataTest {

	private CostcoData costcoData;

	@Before
	public void setUp() {
		costcoData = new CostcoData();
	}

	@Test
	public void parse() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "{"
				+ "\"displayName\" : \"대전점\","
				+ "\"warehouseCode\" : \"costcoKoreaWarehouse\","
				+ "\"name\" : \"Daejeon\","
				+ "\"url\" : \"\\/store\\/Daejeon?lat=35.907757&long=127.766922&q=Korea, Republic of\","
				+ "\"phone\" : \"+82-1899-9900\","
				+ "\"formattedDistance\" : \"56.6 Km\","
				+ "\"line1\" : \"대전광역시 중구 오류로 41\","
				+ "\"line2\" : \"(오류동 116-3)\","
				+ "\"town\" : \"\","
				+ "\"postalCode\" : \"\","
				+ "\"email\" : \"member@costcokr.com\","
				+ "\"latitude\" : \"36.324444\","
				+ "\"longitude\" : \"127.403515\","
				+ "\"storeContent\" : \"\\u003Ch3 class=\\u0027headline\\u0027\\u003E영업시간\\u003C\\/h3\\u003E\\u003Cp style=\\u0027font-size: 20px;\\u0027\\u003E오전 10:00 - 오후 10:00\\u003C\\/p\\u003E\\u003Cp style=\\u0027padding-top:10px;\\u0027\\u003E정부시책에 의거 유통산업 발전법에 따라 \\u003Cspan style=\\u0027color:red;\\u0027\\u003E매월 둘째, 넷째 일요일 의무 휴무\\u003C\\/span\\u003E합니다.\\u003Cbr \\/\\u003E*휴무일: 1\\/1, 설날, 추석 (전일은 오후 7시까지 영업)\\u003C\\/p\\u003E \\u003C\\/body\\u003E \\u003C\\/html\\u003E ※ 일산점은 2월 13일 (수요일) 정상영업 합니다.\", \"openings\": { \"일\":{\"individual\": \"오전 10:00 - 오후 10:00\" } , \"월\":{\"individual\": \"오전 10:00 - 오후 10:00\" } , \"화\":{\"individual\": \"오전 10:00 - 오후 10:00\" } , \"수\":{\"individual\": \"오전 10:00 - 오후 10:00\" } , \"목\":{\"individual\": \"오전 10:00 - 오후 10:00\" } , \"금\":{\"individual\": \"오전 10:00 - 오후 10:00\" } , \"토\":{\"individual\": \"오전 10:00 - 오후 10:00\" } }, \"features\" :[ \"서비스 델리\", \"휠체어 이용가능\", \"타이어 센터\", \"후레쉬 선어\", \"로티세리 치킨\", \"ATM\", \"푸드 코트\", \"후레쉬 정육\", \"후레쉬 델리\", \"후레쉬 청과야채\", \"후레쉬 베이커리\", \"특별 주문대 키오스크\" ], \"image\" : \"\\/medias\\/sys_master\\/images\\/h76\\/hd7\\/10276309139486.jpg\" }";

		costcoData = objectMapper.readValue(json, CostcoData.class);
		Crawlable actual = costcoData;

		assertThat(actual.getRealId()).isEqualTo("Daejeon");
		assertThat(actual.getBranchName()).isEqualTo("대전점");
		assertThat(actual.getRegion()).isEqualTo("대전");
		assertThat(actual.getAddress()).isEqualTo("대전광역시 중구 오류로 41");
		assertThat(actual.getPhoneNumber()).isEqualTo("+82-1899-9900");
		assertThat(actual.getOpeningHours()).isEqualTo("오전 10:00 - 오후 10:00");
		assertThat(actual.getHolidayText()).isEqualTo("매월 둘째, 넷째 일요일");
		assertThat(actual.getHolidays().size()).isGreaterThan(0);
	}

	@Test
	public void addFixedHolidaySameMonthOfNow() {
		CostcoData costcoData = new CostcoData();

		// 같은달이거나 다음달에 고정된 휴무일이 있을 경우 (구정 설날 - 음력 1월1일)
		LocalDate now = LocalDate.of(2019, 2, 1);
		List<Holiday> fixedHolidays = costcoData.getFixedHolidays(now);

		assertThat(fixedHolidays).containsOnly(Holiday.of(2019, 2, 5));
	}

	@Test
	public void addFixedHolidayNextMonthOfNow() {
		// 같은달이거나 다음달에 고정된 휴무일이 있을 경우 (구정 설날 - 음력 1월1일)
		LocalDate now = LocalDate.of(2019, 1, 1);
		List<Holiday> fixedHolidays = costcoData.getFixedHolidays(now);

		assertThat(fixedHolidays).containsExactly(
				Holiday.of(2019, 1, 1),
				Holiday.of(2019, 2, 5)
		);
	}

	@Test
	public void addFixedHolidayNextNewYear() {
		// 마지막 12월
		LocalDate now = LocalDate.of(2019, 12, 1);
		List<Holiday> fixedHolidays = costcoData.getFixedHolidays(now);

		assertThat(fixedHolidays).containsExactly(
				Holiday.of(2020, 1, 1),
				Holiday.of(2020, 1, 25)
		);
	}
}