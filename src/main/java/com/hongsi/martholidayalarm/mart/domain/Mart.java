package com.hongsi.martholidayalarm.mart.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	private Long id;

	@Column(name = "mart_type")
	private MartType martType;

	@Column(name = "real_id")
	private String realId;

	@Column(name = "branch_name")
	private String branchName;

	private String city;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "address")
	private String address;

	@Column
	@OneToMany(mappedBy = "mart", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Holiday> holidays;

	@Builder
	public Mart(MartType martType, String realId, String branchName, String city,
			String phoneNumber, String address, List<Holiday> holidays) {
		this.martType = martType;
		this.realId = realId;
		this.branchName = branchName;
		this.city = city;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.holidays = holidays;
	}

	public void addHoliday(Holiday holiday) {
		if (this.holidays == null) {
			this.holidays = new ArrayList();
		}
		this.holidays.add(holiday);
	}

	public void removeAlreadySavedHoliday(List<Holiday> holidays) {
		for (Holiday holiday : holidays) {
			if (this.holidays.contains(holiday)) {
				this.holidays.remove(holiday);
			}
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setHolidays(List<Holiday> holidays) {
		this.holidays = holidays;
	}

	@Override
	public String toString() {
		return "Mart{" +
				"id=" + id +
				", martType=" + martType +
				", realId='" + realId + '\'' +
				", branchName='" + branchName + '\'' +
				", city='" + city + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", address='" + address + '\'' +
				", holidays=" + holidays +
				'}';
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		Mart mart = (Mart) obj;
		if ((this.martType == mart.martType) && (this.realId.equals(mart.realId))) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = this.martType != null ? this.martType.hashCode() : 0;
		result = 31 * result + (this.realId != null ? this.realId.hashCode() : 0);
		return result;
	}
}
