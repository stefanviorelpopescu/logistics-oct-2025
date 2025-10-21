package org.digitalstack.logistics.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return null;
        }
        return orderStatus.getDbValue();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer dbValue) {
        if (dbValue == null) {
            return null;
        }

        return Stream.of(OrderStatus.values())
                .filter(status -> status.getDbValue() == dbValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
