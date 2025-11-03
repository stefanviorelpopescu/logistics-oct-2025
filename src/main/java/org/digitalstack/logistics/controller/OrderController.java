package org.digitalstack.logistics.controller;

import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.dto.AddOrderDto;
import org.digitalstack.logistics.dto.OrderDto;
import org.digitalstack.logistics.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderDto> getOrders(@RequestParam(required = false, defaultValue = "") String destinationName,
                                    @RequestParam(required = false, defaultValue = "#{applicationData.getCurrentDate()}") LocalDate date) {
        return orderService.getOrders(destinationName, date);
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<OrderDto> cancelOrders(@RequestBody List<Long> orderIds) {
        return orderService.cancelOrders(orderIds);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<OrderDto> addOrders(@RequestBody List<AddOrderDto> orderData) {
        return orderService.addOrders(orderData);
    }
}
