package org.digitalstack.logistics.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.dto.DestinationConverter;
import org.digitalstack.logistics.dto.DestinationDto;
import org.digitalstack.logistics.repository.DestinationRepository;
import org.digitalstack.logistics.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final OrderRepository orderRepository;
    private final EntityManager entityManager;

    public List<DestinationDto> getAllDestinations() {
        return destinationRepository.findAll().stream()
                        .map(DestinationConverter::modelToDto)
                        .toList();
    }

    public DestinationDto getById(Long id) {
        return DestinationConverter.modelToDto(destinationRepository.getReferenceById(id));
    }

    public void deleteById(Long id) {
        orderRepository.deleteAllInBatch(orderRepository.findAllByDestination_id(id));
        entityManager.clear();
        destinationRepository.deleteById(id);
    }
}
