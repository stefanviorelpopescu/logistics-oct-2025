package org.digitalstack.logistics.dto;

import lombok.Builder;

@Builder
public record DestinationDto(
        Long id,
        String name,
        Integer distance) {
}
