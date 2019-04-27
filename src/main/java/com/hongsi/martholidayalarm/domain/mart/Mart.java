package com.hongsi.martholidayalarm.domain.mart;

import com.hongsi.martholidayalarm.exception.CannotChangeException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.Objects;
import java.util.SortedSet;

import static com.hongsi.martholidayalarm.utils.ValidationUtils.isNotBlank;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"martType", "realId"})
})
@DynamicUpdate
@ToString
public class Mart extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private MartType martType;

	@Column(nullable = false, updatable = false)
	private String realId;

	@Column
	private String branchName;

	@Column
	private String region;

	@Column
	private String phoneNumber;

	@Column
	private String address;

	@Column
	private String openingHours;

	@Column
	private String url;

	@Embedded
	private Location location;

	@Column
	private String holidayText;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "holiday", joinColumns = @JoinColumn(name = "mart_id", referencedColumnName = "id"))
	@Fetch(FetchMode.SUBSELECT)
	@SortNatural
	@Where(clause = "date >= CURDATE()")
	private SortedSet<Holiday> holidays;

	@Builder
	public Mart(Long id, MartType martType, String realId, String branchName, String region,
			String phoneNumber, String address, String openingHours, String url,
			Location location, String holidayText, SortedSet<Holiday> holidays) {
		this.id = id;
		this.martType = martType;
		this.realId = realId;
		this.branchName = branchName;
		this.region = region;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.openingHours = openingHours;
		this.url = url;
		this.location = location;
		this.holidayText = holidayText;
		this.holidays = holidays;
	}

	public Mart update(Mart updateMart) {
		if (!equals(updateMart)) {
			throw new CannotChangeException("Can't update. real id and type of mart are different.");
		}

		if (isNotBlank(updateMart.branchName)) branchName = updateMart.branchName;
		if (isNotBlank(updateMart.region)) region = updateMart.region;
		if (isNotBlank(updateMart.phoneNumber)) phoneNumber = updateMart.phoneNumber;
		if (isNotBlank(updateMart.address)) address = updateMart.address;
		if (isNotBlank(updateMart.openingHours)) openingHours = updateMart.openingHours;
		if (isNotBlank(updateMart.url)) url = updateMart.url;
		if (isNotBlank(updateMart.location)) location = updateMart.location;
		if (isNotBlank(updateMart.holidayText)) holidayText = updateMart.holidayText;
		this.holidays = updateMart.holidays;

		return this;
	}

	public Holidays getHolidays() {
		return Holidays.of(holidays);
	}

	public Holiday getUpcomingHoliday() {
		return holidays.first();
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
		return martType == mart.martType &&
				Objects.equals(realId, mart.realId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(martType, realId);
	}
}

