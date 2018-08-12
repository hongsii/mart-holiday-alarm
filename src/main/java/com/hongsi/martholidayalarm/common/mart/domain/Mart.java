package com.hongsi.martholidayalarm.common.mart.domain;

import com.hongsi.martholidayalarm.common.domain.BaseEntity;
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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

	@Column(name = "opening_hours")
	private String openingHours;

	@Column(name = "url")
	private String url;

	@Column
	@OneToMany(mappedBy = "mart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Holiday> holidays = new ArrayList<>();

	@Builder
	public Mart(Long id, MartType martType, String realId, String branchName, String region,
			String phoneNumber, String address, String openingHours, String url,
			List<Holiday> holidays) {
		this.id = id;
		this.martType = martType;
		this.realId = realId;
		this.branchName = branchName;
		this.region = region;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.openingHours = openingHours;
		this.url = url;
		addHolidays(holidays);
	}

	public void addHolidays(List<Holiday> holidays) {
		if (holidays != null) {
			for (Holiday holiday : holidays) {
				addHoliday(holiday);
			}
		}
	}

	public void addHoliday(Holiday holiday) {
		if (!holidays.contains(holiday)) {
			holiday.addedFromMart(this);
			holidays.add(holiday);
		}
	}

	public void update(Mart savedMart) {
		id = savedMart.getId();
		holidays.removeIf(holiday -> savedMart.getHolidays().contains(holiday));
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
				", openingHours='" + openingHours + '\'' +
				", url='" + url + '\'' +
				", holidays=" + holidays +
				", " + super.toString() +
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

		return martType == mart.martType && realId.equals(mart.realId);
	}

	@Override
	public int hashCode() {
		int result = martType.hashCode();
		result = 31 * result + realId.hashCode();
		return result;
	}
}
