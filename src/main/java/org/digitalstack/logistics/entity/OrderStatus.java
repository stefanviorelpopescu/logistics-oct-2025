package org.digitalstack.logistics.entity;

import lombok.Getter;

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
}
