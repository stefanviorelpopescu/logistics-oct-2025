package org.digitalstack.logistics.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity(name = "orders")
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;

    private LocalDate deliveryDate;

    private Long lastUpdated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id")
    private Destination destination;
}
