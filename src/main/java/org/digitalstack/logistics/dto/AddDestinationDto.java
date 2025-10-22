package org.digitalstack.logistics.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record AddDestinationDto(
        @NotEmpty(message = "Destination name should not be empty!")
        String name,
        @Min(value = 1, message = "Distance should be > 0!")
        Integer distance) {
}
