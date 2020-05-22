package com.hongsi.martholidayalarm.core.mart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MartRepository extends JpaRepository<Mart, Long> {

    Optional<Mart> findByRealIdAndMartType(String realId, MartType martType);
}
