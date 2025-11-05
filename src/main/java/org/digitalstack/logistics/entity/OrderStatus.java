package org.digitalstack.logistics.entity;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Getter
public enum OrderStatus {
    NEW(1),
    DELIVERING(2),
    DELIVERED(3),
    CANCELED(4),
    ;

    private final int dbValue;

    OrderStatus(int dbValue) {
        this.dbValue = dbValue;
    }

    public static final Map<OrderStatus, Set<OrderStatus>> allowedTransitions = Map.of(
            NEW, Set.of(DELIVERING, DELIVERED, CANCELED),
            DELIVERING, Set.of(DELIVERED, CANCELED),
            DELIVERED, Collections.emptySet(),
            CANCELED, Collections.emptySet()
    );
}
