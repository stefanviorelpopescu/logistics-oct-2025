package org.digitalstack.logistics.service;

import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.dto.OrderDto;
import org.digitalstack.logistics.dto.converter.OrderConverter;
import org.digitalstack.logistics.entity.Order;
import org.digitalstack.logistics.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderDto> getOrders(String destinationName, LocalDate date) {
        List<Order> orders = orderRepository.findAllByDestination_nameContainingIgnoreCaseAndDeliveryDate(destinationName, date);
        return OrderConverter.modelListToDtoList(orders);
    }
}
