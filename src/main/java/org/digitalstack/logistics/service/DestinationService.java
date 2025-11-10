package org.digitalstack.logistics.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.dto.AddDestinationDto;
import org.digitalstack.logistics.dto.DestinationDto;
import org.digitalstack.logistics.dto.converter.DestinationConverter;
import org.digitalstack.logistics.entity.Destination;
import org.digitalstack.logistics.repository.DestinationRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;

    @Cacheable("destinations")
    public List<DestinationDto> getAllDestinations() {
        return destinationRepository.findAll().stream()
                        .map(DestinationConverter::modelToDto)
                        .toList();
    }

    public DestinationDto getById(Long id) {
        return DestinationConverter.modelToDto(destinationRepository.getDestinationById(id));
    }

    @Caching(
            evict = {
                @CacheEvict(value = "destination", key = "#id"),
                @CacheEvict(value = "destinations", allEntries = true)
            }
    )
    public void deleteById(Long id) {
        destinationRepository.deleteById(id);
    }

    @Caching(
            put = {
                    @CachePut(value = "destinationNames", key = "#result.name")
            },
            evict = {
                    @CacheEvict(value = "destinations", allEntries = true),
                    @CacheEvict(value = "destination", allEntries = true),
            }
    )
    public DestinationDto addDestination(AddDestinationDto request) {

        Optional<Destination> existingDestination = getDestinationByName(request.name());
        if (existingDestination.isPresent()) {
            throw new EntityExistsException(String.format("Destination with name %s already exists!", request.name()));
        }

        Destination destination = Destination.builder()
                .name(request.name())
                .distance(request.distance())
                .build();
        Destination savedDestination = destinationRepository.save(destination);
        return DestinationConverter.modelToDto(savedDestination);
    }

    @Cacheable(value = "destinationNames", key = "#name")
    private Optional<Destination> getDestinationByName(String name) {
        return destinationRepository.findByName(name);
    }

    @Caching(
            put = {
                    @CachePut(value = "destinationNames", key = "#result.name")
            },
            evict = {
                    @CacheEvict(value = "destination", allEntries = true),
                    @CacheEvict(value = "destinations", allEntries = true),
                    @CacheEvict(value = "destinationNames", key = "#request.name")
            }
    )
    public DestinationDto editDestination(DestinationDto request) {
        destinationRepository.findById(request.id())
                .orElseThrow(EntityNotFoundException::new);
        getDestinationByName(request.name())
                .ifPresent(destination -> checkDestinationNameIsNotInUse(request, destination));

        Destination toBeSaved = DestinationConverter.dtoToModel(request);
        Destination savedDestination = destinationRepository.save(toBeSaved);
        return DestinationConverter.modelToDto(savedDestination);
    }

    private void checkDestinationNameIsNotInUse(DestinationDto request, Destination destination) {
        if (!destination.getId().equals(request.id())) {
            throw new EntityExistsException(String.format("The destination name %s is already in use!", request.name()));
        }
    }
}
