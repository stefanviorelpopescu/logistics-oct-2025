package org.digitalstack.logistics.controller;

import lombok.RequiredArgsConstructor;
import org.digitalstack.logistics.service.ShippingService;
import org.digitalstack.logistics.service.exception.DayNotOverException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping("/new-day")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String newDay() throws DayNotOverException {
        return shippingService.newDay();
    }

}
