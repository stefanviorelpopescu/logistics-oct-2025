package org.digitalstack.logistics.repository;

import org.digitalstack.logistics.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByDestination_nameContainingIgnoreCaseAndDeliveryDate(String destinationName, LocalDate date);

    List<Order> findAllByDeliveryDate(LocalDate deliveryDate);
}
