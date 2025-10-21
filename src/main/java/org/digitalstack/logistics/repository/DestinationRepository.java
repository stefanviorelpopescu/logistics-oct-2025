package org.digitalstack.logistics.repository;

import org.digitalstack.logistics.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
