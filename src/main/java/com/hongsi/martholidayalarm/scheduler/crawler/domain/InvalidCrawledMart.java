package com.hongsi.martholidayalarm.scheduler.crawler.domain;

import com.hongsi.martholidayalarm.domain.mart.BaseEntity;
import com.hongsi.martholidayalarm.domain.mart.MartType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"martType", "realId"})
})
public class InvalidCrawledMart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MartType martType;

    @Column(nullable = false)
    private String realId;

    @Column(nullable = false)
    private Boolean enable;

    @Builder
    public InvalidCrawledMart(MartType martType, String realId, Boolean enable) {
        if (martType == null || realId == null) {
            throw new IllegalArgumentException("MartType and RealId must be non-null");
        }
        this.martType = martType;
        this.realId = realId;
        this.enable = Optional.ofNullable(enable).orElse(Boolean.TRUE);
    }

    public boolean isInvalid(MartType martType, String realId) {
        if (!enable) {
            return false;
        }
        return this.martType == martType && this.realId.equals(realId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvalidCrawledMart that = (InvalidCrawledMart) o;
        return martType == that.martType &&
                Objects.equals(realId, that.realId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(martType, realId);
    }
}