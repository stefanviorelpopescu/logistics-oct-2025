package org.digitalstack.logistics.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.digitalstack.logistics.config.ApplicationData;
import org.digitalstack.logistics.entity.Destination;
import org.digitalstack.logistics.entity.Order;
import org.digitalstack.logistics.entity.OrderStatus;
import org.digitalstack.logistics.repository.OrderRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryManager {

    private final OrderRepository orderRepository;
    private final ApplicationData applicationData;

    @SneakyThrows
    @Async("deliveryExecutor")
    public void deliver(Destination destination, List<Long> orderIdsToDeliver) {
        log.info("Delivering to {}", destination.getName());

        List<Long> deliveringOrderIds = orderRepository.changeOrdersStatus(orderIdsToDeliver, OrderStatus.DELIVERING).stream()
                .map(Order::getId)
                .toList();

        Thread.sleep(destination.getDistance() * 1000);

        List<Order> deliveredOrders = orderRepository.changeOrdersStatus(deliveringOrderIds, OrderStatus.DELIVERED);

        applicationData.incrementProfit((long) deliveredOrders.size() * destination.getDistance());
    }
}
