package org.digitalstack.logistics.service;

import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.config.ApplicationData;
import org.digitalstack.logistics.dto.AddOrderDto;
import org.digitalstack.logistics.dto.OrderDto;
import org.digitalstack.logistics.dto.converter.OrderConverter;
import org.digitalstack.logistics.entity.Order;
import org.digitalstack.logistics.entity.OrderStatus;
import org.digitalstack.logistics.repository.DestinationRepository;
import org.digitalstack.logistics.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ApplicationData applicationData;
    private final OrderRepository orderRepository;
    private final DestinationRepository destinationRepository;

    public List<OrderDto> getOrders(String destinationName, LocalDate date) {
        List<Order> orders = orderRepository.findAllByDestination_nameContainingIgnoreCaseAndDeliveryDate(destinationName, date);
        return OrderConverter.modelListToDtoList(orders);
    }

    @Transactional
    public List<OrderDto> cancelOrders(List<Long> orderIds) {
        List<Order> savedOrders = orderRepository.changeOrdersStatus(orderIds, OrderStatus.CANCELED);
        return OrderConverter.modelListToDtoList(savedOrders);
    }

    public List<OrderDto> addOrders(List<AddOrderDto> orderData) {
        long currentTime = System.currentTimeMillis();
        List<Order> ordersToSave = new ArrayList<>();
        orderData.stream()
                .filter(orderDatum -> orderDatum.deliveryDate().isAfter(applicationData.getCurrentDate()))
                .forEach(orderDatum -> ordersToSave.add(
                        Order.builder()
                                .status(OrderStatus.NEW)
                                .deliveryDate(orderDatum.deliveryDate())
                                .destination(destinationRepository.getReferenceById(orderDatum.destinationId()))
                                .lastUpdated(currentTime)
                                .build()
                ));
        List<Order> savedOrders = orderRepository.saveAll(ordersToSave);
        return OrderConverter.modelListToDtoList(savedOrders);
    }
}
