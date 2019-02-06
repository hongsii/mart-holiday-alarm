package com.hongsi.martholidayalarm.domain.crawler.holiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.Test;

public class SolarLunarConverterTest {

	@Test
	public void convertLunarToSolarByNewYear() {
		LocalDate newYearOfLunar = LocalDate.of(2019, 1, 1);

		LocalDate newYearOfSolar = SolarLunarConverter.convertLunarToSolar(newYearOfLunar);

		assertThat(newYearOfSolar).isEqualTo(LocalDate.of(2019, 2, 5));
	}

	@Test
	public void convertLunarToSolarByChuseok() {
		LocalDate chuSeokOfLunar = LocalDate.of(2019, 8, 15);

		LocalDate chuSeokOfSolar = SolarLunarConverter.convertLunarToSolar(chuSeokOfLunar);

		assertThat(chuSeokOfSolar).isEqualTo(LocalDate.of(2019, 9, 13));
	}
}