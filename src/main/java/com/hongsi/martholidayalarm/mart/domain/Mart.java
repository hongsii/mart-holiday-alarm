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
	public Mart(MartType martType, String realId) {
		this.martType = martType;
		this.realId = realId;
	}

	public void addHoliday(Holiday holiday) {
		if (holidays == null) {
			holidays = new ArrayList<>();
		}
		holidays.add(holiday);
	}
}
