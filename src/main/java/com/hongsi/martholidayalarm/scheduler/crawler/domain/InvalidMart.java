package com.hongsi.martholidayalarm.scheduler.crawler.domain;

import com.hongsi.martholidayalarm.domain.mart.BaseEntity;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"martType", "realId"})
})
public class InvalidMart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MartType martType;

    @Column(nullable = false)
    private String realId;

    @Builder
    public InvalidMart(MartType martType, String realId) {
        this.martType = martType;
        this.realId = realId;
    }

    public boolean isInvalid(MartType martType, String realId) {
        return this.martType == martType && this.realId.equals(realId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidMart that = (InvalidMart) o;
        return martType == that.martType &&
                Objects.equals(realId, that.realId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(martType, realId);
    }
}