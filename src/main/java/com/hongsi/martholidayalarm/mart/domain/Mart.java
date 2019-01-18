package com.hongsi.martholidayalarm.mart.domain;

import static com.hongsi.martholidayalarm.common.utils.ValidationUtils.isNotBlank;

import com.hongsi.martholidayalarm.common.domain.BaseEntity;
import com.hongsi.martholidayalarm.mart.dto.Holidays;
import com.hongsi.martholidayalarm.mart.exception.CannotChangeException;
import java.util.Objects;
import java.util.SortedSet;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SortNatural;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@Getter
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = {"mart_type", "real_id"})
})
@DynamicUpdate
@ToString
public class Mart extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "mart_type", nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private MartType martType;

	@Column(name = "real_id", nullable = false, updatable = false)
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

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "holiday", joinColumns = @JoinColumn(name = "mart_id", referencedColumnName = "id"))
	@Fetch(FetchMode.SUBSELECT)
	@SortNatural
	@Where(clause = "date >= CURDATE()")
	private SortedSet<Holiday> holidays;

	@Builder
	public Mart(Long id, MartType martType, String realId, String branchName, String region,
			String phoneNumber, String address, String openingHours, String url,
			SortedSet<Holiday> holidays) {
		this.id = id;
		this.martType = martType;
		this.realId = realId;
		this.branchName = branchName;
		this.region = region;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.openingHours = openingHours;
		this.url = url;
		this.holidays = holidays;
	}

	public Mart update(Mart updateMart) {
		if (!equals(updateMart)) {
			throw new CannotChangeException(
					"Can't update. real id and type of mart are different.");
		}

		if (isNotBlank(updateMart.branchName)) {
			branchName = updateMart.branchName;
		}
		if (isNotBlank(updateMart.region)) {
			region = updateMart.region;
		}
		if (isNotBlank(updateMart.phoneNumber)) {
			phoneNumber = updateMart.phoneNumber;
		}
		if (isNotBlank(updateMart.address)) {
			address = updateMart.address;
		}
		if (isNotBlank(updateMart.openingHours)) {
			openingHours = updateMart.openingHours;
		}
		if (isNotBlank(updateMart.url)) {
			url = updateMart.url;
		}
		this.holidays = updateMart.holidays;
		return this;
	}

	public Holidays getHolidays() {
		return Holidays.of(holidays);
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

