package org.digitalstack.logistics.dto.converter;

import org.digitalstack.logistics.dto.OrderDto;
import org.digitalstack.logistics.entity.Order;

import java.util.List;

public class OrderConverter {

    public static OrderDto modelToDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .status(order.getStatus())
                .deliveryDate(order.getDeliveryDate())
                .lastUpdated(order.getLastUpdated())
                .destinationName(order.getDestination().getName())
                .build();
    }

    public static List<OrderDto> modelListToDtoList(List<Order> orderList) {
        return orderList.stream()
                .map(OrderConverter::modelToDto)
                .toList();
    }

}
