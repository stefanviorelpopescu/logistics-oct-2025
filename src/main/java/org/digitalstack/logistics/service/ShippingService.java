package org.digitalstack.logistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.digitalstack.logistics.config.ApplicationData;
import org.digitalstack.logistics.entity.Destination;
import org.digitalstack.logistics.entity.Order;
import org.digitalstack.logistics.repository.OrderRepository;
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
//    private final Executor deliveryExecutor;
    private final DeliveryManager deliveryManager;

    public String newDay() {
        LocalDate currentDate = applicationData.incrementAndGetDate();

        Map<Destination, List<Long>> ordersByDestination = orderRepository.findAllByDeliveryDate(currentDate).stream()
                .collect(Collectors.groupingBy(Order::getDestination, Collectors.mapping(Order::getId, Collectors.toList())));

        //start delivering
//        ordersByDestination.forEach((destination, orderList) ->
//                deliveryExecutor.execute(new DeliveryRunnable(destination, orderList, orderRepository)));

        ordersByDestination.forEach(deliveryManager::deliver);

        return String.format("New day started: %s", currentDate);
    }

}
