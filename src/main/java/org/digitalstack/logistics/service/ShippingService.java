package org.digitalstack.logistics.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.digitalstack.logistics.config.ApplicationData;
import org.digitalstack.logistics.entity.Destination;
import org.digitalstack.logistics.entity.Order;
import org.digitalstack.logistics.repository.OrderRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingService {

    private final ApplicationData applicationData;
    private final OrderRepository orderRepository;
    private final Executor deliveryExecutor;

    public String newDay() {
        LocalDate currentDate = applicationData.incrementAndGetDate();

        Map<Destination, List<Order>> ordersByDestination = orderRepository.findAllByDeliveryDate(currentDate).stream()
                .collect(Collectors.groupingBy(Order::getDestination));

        //start delivering
//        ordersByDestination.forEach((destination, orderList) ->
//                deliveryExecutor.execute(new DeliveryRunnable(destination, orderList)));

        ordersByDestination.forEach(this::deliver);

        return String.format("New day started: %s", currentDate);
    }

    @SneakyThrows
    @Async("deliveryExecutor")
    private void deliver(Destination destination, List<Order> orders) {
        log.info("Delivering to {}", destination.getName());
        Thread.sleep(destination.getDistance() * 1000);
    }
}
