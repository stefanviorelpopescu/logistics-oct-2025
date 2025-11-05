package org.digitalstack.logistics.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.digitalstack.logistics.entity.Destination;
import org.digitalstack.logistics.entity.Order;
import org.digitalstack.logistics.entity.OrderStatus;
import org.digitalstack.logistics.repository.OrderRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DeliveryRunnable implements Runnable{

    private final Destination destination;
    private final List<Order> orders;
    private final OrderRepository orderRepository;

    @SneakyThrows
    @Override
    public void run() {
        log.info("Delivering to {}", destination.getName());

        orders.forEach(order -> order.setStatus(OrderStatus.DELIVERING));
        orderRepository.saveAll(orders);

        Thread.sleep(destination.getDistance() * 1000);

        orders.forEach(order -> order.setStatus(OrderStatus.DELIVERED));
        orderRepository.saveAll(orders);
    }
}
