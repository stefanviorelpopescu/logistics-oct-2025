package org.digitalstack.logistics.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.digitalstack.logistics.entity.Destination;
import org.digitalstack.logistics.entity.Order;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DeliveryRunnable implements Runnable{

    private final Destination destination;
    private final List<Order> orders;

    @SneakyThrows
    @Override
    public void run() {
        log.info("Delivering to {}", destination.getName());
        Thread.sleep(destination.getDistance() * 1000);
    }
}
