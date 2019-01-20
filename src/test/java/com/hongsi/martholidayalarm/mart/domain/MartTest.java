package com.hongsi.martholidayalarm.mart.domain;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import com.hongsi.martholidayalarm.mart.dto.Holidays;
import com.hongsi.martholidayalarm.mart.dto.MartResponse;
import com.hongsi.martholidayalarm.mart.exception.CannotChangeException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Before;
import org.junit.Test;

public class MartTest {

	public static final Mart newMart = Mart.builder()
			.martType(MartType.EMART)
			.realId("1")
			.region("서울")
			.branchName("성수점")
			.address("주소")
			.phoneNumber("02-1234-5678")
			.openingHours("10:00 ~ 11:00")
			.build();

	public static final List<MartResponse> newMartsResponse = asList(
			new MartResponse(
					Mart.builder()
							.id(1L)
							.martType(MartType.EMART)
							.branchName("가든5점")
							.region("서울")
							.phoneNumber("02-411-1234")
							.address("서울특별시 송파구 충민로 10")
							.openingHours("10:00~23:00")
							.url("http://store.emart.com/branch/list.do?id=1140")
							.holidays(new TreeSet<>(asList(Holiday.of(2019, 1, 1), Holiday.of(2019, 1, 11))))
							.build()
					),
			new MartResponse(
					Mart.builder()
							.id(5L)
							.martType(MartType.EMART)
							.branchName("검단점")
							.region("인천")
							.phoneNumber("032-440-1234")
							.address("인천광역시 서구 서곶로 754")
							.openingHours("10:00~23:00")
							.url("http://store.emart.com/branch/list.do?id=1101")
							.holidays(new TreeSet<>(asList(Holiday.of(2019, 1, 1), Holiday.of(2019, 1, 11))))
							.build()
			)
	);

	private Mart mart;

	public static Mart create(MartType martType, String realId) {
		return Mart.builder()
				.martType(martType)
				.realId(realId)
				.build();
	}

	public static SortedSet<Holiday> createHolidays(Holiday... holidays) {
		return Arrays.stream(holidays)
				.collect(Collectors.toCollection(TreeSet::new));
	}

	@Before
	public void setUp() {
		mart = newMart;
	}

	@Test
	public void 타입과_고유아이디가_동일한_마트() {
		Mart mart = Mart.builder()
				.id(1L)
				.martType(MartType.EMART)
				.realId("1")
				.branchName("성수점")
				.build();

		assertThat(this.mart).isEqualTo(mart);
	}

	@Test
	public void 타입과_고유아이디가_다른_마트() {
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("2")
				.branchName("성수점")
				.build();

		assertThat(this.mart).isNotEqualTo(mart);
	}

	@Test
	public void 업데이트() {
		String region = "부산", branchName = "부산점", address = "변경된 주소";
		String phoneNumber = "02-5678-1234", openingHours = "11:00 ~ 12:00";
		SortedSet<Holiday> holidays = Stream.of(
				Holiday.of(LocalDate.now()),
				Holiday.of(LocalDate.now().plusDays(1)))
				.collect(Collectors.toCollection(TreeSet::new));
		Mart updateMart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.region(region)
				.branchName(branchName)
				.address(address)
				.phoneNumber(phoneNumber)
				.openingHours(openingHours)
				.holidays(holidays)
				.build();

		mart.update(updateMart);

		assertThat(mart)
				.extracting(Mart::getRegion, Mart::getBranchName, Mart::getAddress,
						Mart::getPhoneNumber, Mart::getOpeningHours, Mart::getHolidays)
				.containsExactly(region, branchName, address, phoneNumber, openingHours,
						Holidays.of(holidays));
	}

	@Test(expected = CannotChangeException.class)
	public void 고유아이디가_다르면_업데이트_할수없음() {
		Mart updateMart = Mart.builder()
				.martType(MartType.EMART)
				.realId("2")
				.build();

		mart.update(updateMart);
	}

	@Test(expected = CannotChangeException.class)
	public void 마트타입이_다르면_업데이트_할수없음() {
		Mart updateMart = Mart.builder()
				.martType(MartType.LOTTEMART)
				.realId("1")
				.build();

		mart.update(updateMart);
	}

	@Test
	public void 비어있는_값은_업데이트하지않음() {
		String region = mart.getRegion(), branchName = mart.getBranchName();
		String address = mart.getAddress(), phoneNumber = mart.getPhoneNumber();
		String openingHours = mart.getOpeningHours();

		mart.update(Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build());

		assertThat(mart)
				.extracting(Mart::getRegion, Mart::getBranchName, Mart::getAddress,
						Mart::getPhoneNumber, Mart::getOpeningHours)
				.containsExactly(region, branchName, address, phoneNumber, openingHours);
	}

	@Test
	public void 리스트_내의_마트_찾기() {
		List<Mart> marts = asList(create(MartType.EMART, "1"), create(MartType.EMART, "2"));

		assertThat(marts).contains(newMart);
	}
}