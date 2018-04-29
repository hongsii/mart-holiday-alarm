package com.hongsi.martholidayalarm.mart.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Mart extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mart_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private MartType martType;

	@Column(name = "real_id", nullable = false)
	private String realId;

	@Column(name = "branch_name")
	private String branchName;

	@Column(name = "region")
	private String region;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "address")
	private String address;

	@Column(name = "url")
	private String url;

	@Column
	@OneToMany(mappedBy = "mart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Holiday> holidays = new ArrayList<>();

	@Builder
	public Mart(MartType martType, String realId, String branchName, String region,
			String phoneNumber, String address, String url) {
		this.martType = martType;
		this.realId = realId;
		this.branchName = branchName;
		this.region = region;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.url = url;
	}

	public void addHolidays(List<Holiday> holidays) {
		for (Holiday holiday : holidays) {
			addHoliday(holiday);
		}
	}

	public void addHoliday(Holiday holiday) {
		if (!holidays.contains(holiday)) {
			holidays.add(holiday);
		}
	}

	public void readyForUpdate(Mart savedMart) {
		id = savedMart.getId();
		List<Holiday> savedHolidays = savedMart.getHolidays();
		for (Holiday holiday : holidays) {
			if (savedHolidays.contains(holiday)) {
				int index = savedHolidays.indexOf(holiday);
				holiday.readyForUpdate(savedHolidays.get(index));
			}
		}
	}

	@Override
	public String toString() {
		return "Mart{" +
				"id=" + id +
				", martType=" + martType +
				", realId='" + realId + '\'' +
				", branchName='" + branchName + '\'' +
				", region='" + region + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", address='" + address + '\'' +
				", holidays=" + holidays +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Mart)) {
			return false;
		}

		Mart mart = (Mart) o;

		if (martType != mart.martType) {
			return false;
		}
		return realId.equals(mart.realId);
	}

	@Override
	public int hashCode() {
		int result = martType.hashCode();
		result = 31 * result + realId.hashCode();
		return result;
	}
}
