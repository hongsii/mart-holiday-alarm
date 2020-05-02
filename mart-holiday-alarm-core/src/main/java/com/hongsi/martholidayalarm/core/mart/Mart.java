package com.hongsi.martholidayalarm.core.mart;

import com.hongsi.martholidayalarm.core.BaseEntity;
import com.hongsi.martholidayalarm.core.holiday.Holiday;
import com.hongsi.martholidayalarm.core.location.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"martType", "realId"})
})
@NoArgsConstructor
@Getter
public class Mart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private MartType martType;

    @Column(nullable = false, updatable = false)
    private String realId;

    private String branchName;

    private String region;

    private String phoneNumber;

    private String address;

    private String openingHours;

    private String url;

    private String holidayText;

    @Embedded
    private Location location;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "holiday", joinColumns = @JoinColumn(name = "mart_id", referencedColumnName = "id"))
    @Fetch(FetchMode.SUBSELECT)
    private Set<Holiday> holidays;

    @Builder
    public Mart(Long id, MartType martType, String realId, String branchName, String region,
                String phoneNumber, String address, String openingHours, String url,
                Location location, String holidayText, Set<Holiday> holidays) {
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

    public List<Holiday> getUpcomingHolidays() {
        return holidays.stream()
                .filter(Holiday::isUpcoming)
                .sorted()
                .collect(Collectors.toList());
    }
}
