package org.digitalstack.logistics.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AddOrderDto(
        @NotNull
        LocalDate deliveryDate,
        @NotNull
        Long destinationId
) {}
