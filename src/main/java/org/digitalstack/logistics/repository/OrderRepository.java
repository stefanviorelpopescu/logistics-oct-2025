package org.digitalstack.logistics.repository;

import jakarta.persistence.LockModeType;
import org.digitalstack.logistics.entity.Order;
import org.digitalstack.logistics.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.digitalstack.logistics.entity.OrderStatus.*;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByDestination_nameContainingIgnoreCaseAndDeliveryDate(String destinationName, LocalDate date);

    List<Order> findAllByDeliveryDate(LocalDate deliveryDate);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    default List<Order> changeOrdersStatus(List<Long> orderIds, OrderStatus newStatus) {
        List<Order> ordersToChange = findAllById(orderIds);
        List<Order> ordersToSave = new ArrayList<>();
        for (Order order : ordersToChange) {
            if (allowedTransitions.get(order.getStatus()).contains(newStatus)) {
                order.setStatus(newStatus);
                ordersToSave.add(order);
            }
        }
        return saveAll(ordersToSave);
    }
}
