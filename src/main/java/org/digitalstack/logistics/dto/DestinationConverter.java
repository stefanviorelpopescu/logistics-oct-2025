package org.digitalstack.logistics.dto;

import org.digitalstack.logistics.entity.Destination;

public class DestinationConverter {

    public static DestinationDto modelToDto(Destination destination) {
        return DestinationDto.builder()
                .id(destination.getId())
                .name(destination.getName())
                .distance(destination.getDistance())
                .build();
    }

    public static Destination dtoToModel(DestinationDto destinationDto) {
        return Destination.builder()
                .id(destinationDto.id())
                .name(destinationDto.name())
                .distance(destinationDto.distance())
                .build();
    }

}
