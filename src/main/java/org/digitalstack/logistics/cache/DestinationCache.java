package org.digitalstack.logistics.cache;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.entity.Destination;
import org.digitalstack.logistics.repository.DestinationRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DestinationCache {

    private final DestinationRepository destinationRepository;

    private Map<Long, Destination> destinationsById;
    private Map<String, Destination> destinationsByName;

    @EventListener(ApplicationReadyEvent.class)
    public void reload() {
        destinationsById = new HashMap<>();
        destinationsByName = new HashMap<>();
        destinationRepository.findAll().forEach(destination ->
                {
                    destinationsById.put(destination.getId(), destination);
                    destinationsByName.put(destination.getName(), destination);
                }
        );
    }

    public Destination getDestination(Long id) {
        if (!destinationsById.containsKey(id)) {
            throw new EntityNotFoundException();
        }
        return destinationsById.get(id);
    }

    public Optional<Destination> getOptionalDestination(Long id) {
        return Optional.ofNullable(destinationsById.get(id));
    }

    public Collection<Destination> getAllDestinations() {
        return destinationsById.values();
    }

    public Optional<Destination> getDestinationByName(String name) {
        return Optional.ofNullable(destinationsByName.get(name));
    }

    public void deleteById(Long id) {
        destinationsByName.remove(destinationsById.get(id).getName());
        destinationsById.remove(id);
    }

    public void addDestination(Destination savedDestination) {
        destinationsById.put(savedDestination.getId(), savedDestination);
        destinationsByName.put(savedDestination.getName(), savedDestination);
    }

    public void updateDestination(Destination updatedDestination) {
        destinationsByName.remove(destinationsById.get(updatedDestination.getId()).getName());
        destinationsById.put(updatedDestination.getId(), updatedDestination);
        destinationsByName.put(updatedDestination.getName(), updatedDestination);
    }
}
