package com.hongsi.martholidayalarm.mart.service;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import com.hongsi.martholidayalarm.mart.repository.MartRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MartServiceTest {

	@Autowired
	MartService martService;

	@Autowired
	MartRepository martRepository;

	@After
	public void cleanup() {
		martRepository.deleteAll();
	}

	@Test
	public void 마트_전체_저장() {
		List<Mart> marts = new ArrayList<>();
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		mart.addHoliday(Holiday.builder()
				.mart(mart)
				.date(LocalDate.of(2018, 3, 31))
				.build());
		marts.add(mart);
		martService.saveAll(marts);

		marts = new ArrayList<>();
		mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.branchName("성수점")
				.build();
		mart.addHoliday(Holiday.builder()
				.mart(mart)
				.date(LocalDate.of(2018, 3, 31))
				.build());
		marts.add(mart);
		martService.saveAll(marts);

		marts = martRepository.findAll();
		assertEquals(1, marts.size());
		assertEquals(1, marts.get(0).getHolidays().size());
		assertEquals("성수점", marts.get(0).getBranchName());
	}

	@Test
	public void 마트타입_조회() {
		List<Mart> marts = new ArrayList<>();
		marts.add(Mart.builder()
				.realId("1")
				.martType(MartType.EMART)
				.region("서울")
				.build());
		marts.add(Mart.builder()
				.realId("2")
				.martType(MartType.EMART)
				.region("경상")
				.build());
		marts.add(Mart.builder()
				.realId("1")
				.martType(MartType.LOTTEMART)
				.region("서울")
				.build());
		marts.add(Mart.builder()
				.realId("2")
				.martType(MartType.LOTTEMART)
				.region("경상")
				.build());
		martRepository.saveAll(marts);

		List<MartType> martTypes = martService.getMartTypes();
		List<MartType> usingMartTypes = Arrays.stream(MartType.values())
				.collect(Collectors.toList());
		assertEquals(martTypes.size(), usingMartTypes.size());
	}

	@Test
	public void 마트타입별_지역_조회() {
		List<Mart> marts = new ArrayList<>();
		marts.add(Mart.builder()
				.realId("1")
				.martType(MartType.EMART)
				.region("서울")
				.build());
		marts.add(Mart.builder()
				.realId("2")
				.martType(MartType.EMART)
				.region("경상")
				.build());
		marts.add(Mart.builder()
				.realId("3")
				.martType(MartType.EMART)
				.region("경기")
				.build());
		martRepository.saveAll(marts);

		List<String> regions = martService.getRegions(MartType.EMART);

		assertEquals(marts.size(), regions.size());
		assertThat(regions, containsInAnyOrder("경기", "경상", "서울"));
	}

	@Test
	public void 마트타입별_지점_조회() {
		List<Mart> marts = new ArrayList<>();
		marts.add(Mart.builder()
				.realId("1")
				.martType(MartType.EMART)
				.region("서울")
				.branchName("서울점")
				.build());
		marts.add(Mart.builder()
				.realId("2")
				.martType(MartType.EMART)
				.region("서울")
				.branchName("강남점")
				.build());
		marts.add(Mart.builder()
				.realId("3")
				.martType(MartType.EMART)
				.region("경상")
				.branchName("경상점")
				.build());
		marts.add(Mart.builder()
				.realId("1")
				.martType(MartType.LOTTEMART)
				.region("경기")
				.branchName("경기점")
				.build());
		martRepository.saveAll(marts);

		List<String> branches = martService.getBranches(MartType.EMART, "서울");

		assertEquals(2, branches.size());
		assertThat(branches, containsInAnyOrder("강남점", "서울점"));
	}
}