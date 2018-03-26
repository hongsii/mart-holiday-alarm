package com.hongsi.martholidayalarm.mart.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.hongsi.martholidayalarm.mart.domain.Holiday;
import com.hongsi.martholidayalarm.mart.domain.Mart;
import com.hongsi.martholidayalarm.mart.domain.MartType;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HolidayRepositoryTest {

	@Autowired
	MartRepository martRepository;
	@Autowired
	HolidayRepository holidayRepository;

	@After
	public void cleanup() {
		martRepository.deleteAll();
		holidayRepository.deleteAll();
	}

	@Test
	public void 휴무일조회() {
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		LocalDate now = LocalDate.now();
		Holiday holiday = Holiday.builder()
				.date(now)
				.mart(mart)
				.build();
		mart.addHoliday(holiday);
		martRepository.save(mart);

		List<Holiday> holidays = holidayRepository.findAll();
		Holiday savedHoliday = holidays.get(0);

		assertNotNull(savedHoliday);
		assertEquals(mart.getId(), savedHoliday.getMart().getId());
		assertEquals(now, savedHoliday.getHoliday());
	}

	@Test
	public void 지점별로_같은휴무일이_중복저장_안되는지_확인() {
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		LocalDate now = LocalDate.now();
		Holiday holiday = Holiday.builder()
				.date(now)
				.mart(mart)
				.build();
		mart.addHoliday(holiday);
		martRepository.save(mart);

		List<Mart> marts = martRepository.findAll();
		Holiday holiday2 = Holiday.builder()
				.date(now)
				.mart(marts.get(0))
				.build();
		mart.addHoliday(holiday2);
		martRepository.save(marts.get(0));

		List<Holiday> holidays = holidayRepository.findAll();
		assertEquals(1, holidays.size());
	}

	@Test
	public void 마트별로_같은휴무일_저장되는지_확인() {
		LocalDate now = LocalDate.now();
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		Holiday holiday = Holiday.builder()
				.date(now)
				.mart(mart)
				.build();
		mart.addHoliday(holiday);
		martRepository.save(mart);

		Mart mart2 = Mart.builder()
				.martType(MartType.EMART)
				.realId("2")
				.build();
		Holiday holiday2 = Holiday.builder()
				.date(now)
				.mart(mart2)
				.build();
		mart2.addHoliday(holiday2);
		martRepository.save(mart2);

		List<Mart> marts = martRepository.findAll();
		Mart savedMart1 = marts.get(0);
		Mart savedMart2 = marts.get(1);

		assertEquals(2, marts.size());
		assertEquals(now, savedMart1.getHolidays().get(0).getHoliday());
		assertEquals(savedMart1.getHolidays().get(0).getHoliday(),
				savedMart2.getHolidays().get(0).getHoliday());
	}

	@Test
	public void 휴무일을_삭제해도_마트는_지워지지_않는지_확인() {
		Mart mart = Mart.builder()
				.martType(MartType.EMART)
				.realId("1")
				.build();
		Holiday holiday = Holiday.builder()
				.date(LocalDate.now())
				.mart(mart)
				.build();
		mart.addHoliday(holiday);
		martRepository.save(mart);

		holidayRepository.deleteAll();
		List<Mart> marts = martRepository.findAll();

		assertEquals(1, marts.size());
	}
}