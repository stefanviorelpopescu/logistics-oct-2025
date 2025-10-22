package org.digitalstack.logistics.dto;

import lombok.Builder;
import org.digitalstack.logistics.entity.OrderStatus;

import java.time.LocalDate;

@Builder
public record OrderDto(
        Long id,
        OrderStatus status,
        LocalDate deliveryDate,
        Long lastUpdated,
        String destinationName
) {
}
