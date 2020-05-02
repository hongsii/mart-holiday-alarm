package com.hongsi.martholidayalarm.api.dto.mart;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction;
import static org.springframework.data.domain.Sort.Order;

public enum MartOrder {

    id, martType, branchName, region;

    private static final String COLUMN_ERROR_MESSAGE = String.format("일치하는 정렬 컬럼이 없습니다.\n- 가능한 정렬 : [%s]",
            Arrays.stream(values())
                    .map(MartOrder::name)
                    .collect(Collectors.joining(", "))
    );

    public static MartOrder of(String name) {
        return Arrays.stream(values())
                .filter(property -> property.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(COLUMN_ERROR_MESSAGE));
    }

    public Order asc() {
        return Order.asc(name());
    }

    public Order desc() {
        return Order.desc(name());
    }

    public Order by(String direction) {
        return new Order(Direction.fromString(direction), name());
    }
}
