package org.digitalstack.logistics.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.cache.DestinationCache;
import org.digitalstack.logistics.dto.AddDestinationDto;
import org.digitalstack.logistics.dto.converter.DestinationConverter;
import org.digitalstack.logistics.dto.DestinationDto;
import org.digitalstack.logistics.entity.Destination;
import org.digitalstack.logistics.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final DestinationCache destinationCache;

    public List<DestinationDto> getAllDestinations() {
        return destinationCache.getAllDestinations().stream()
                        .map(DestinationConverter::modelToDto)
                        .toList();
    }

    public DestinationDto getById(Long id) {
        return DestinationConverter.modelToDto(destinationCache.getDestination(id));
    }

    public void deleteById(Long id) {
        destinationRepository.deleteById(id);
        destinationCache.deleteById(id);
    }

    public DestinationDto addDestination(AddDestinationDto request) {

        Optional<Destination> existingDestination = destinationCache.getDestinationByName(request.name());
        if (existingDestination.isPresent()) {
            throw new EntityExistsException(String.format("Destination with name %s already exists!", request.name()));
        }

        Destination destination = Destination.builder()
                .name(request.name())
                .distance(request.distance())
                .build();
        Destination savedDestination = destinationRepository.save(destination);
        destinationCache.addDestination(savedDestination);
        return DestinationConverter.modelToDto(savedDestination);
    }

    public DestinationDto editDestination(DestinationDto request) {
        destinationCache.getOptionalDestination(request.id())
                .orElseThrow(EntityNotFoundException::new);
        destinationCache.getDestinationByName(request.name())
                .ifPresent(destination -> checkDestinationNameIsNotInUse(request, destination));

        Destination toBeSaved = DestinationConverter.dtoToModel(request);
        Destination savedDestination = destinationRepository.save(toBeSaved);
        destinationCache.updateDestination(savedDestination);
        return DestinationConverter.modelToDto(savedDestination);
    }

    private void checkDestinationNameIsNotInUse(DestinationDto request, Destination destination) {
        if (!destination.getId().equals(request.id())) {
            throw new EntityExistsException(String.format("The destination name %s is already in use!", request.name()));
        }
    }
}
