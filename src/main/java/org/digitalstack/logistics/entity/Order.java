package org.digitalstack.logistics.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;

    private LocalDate deliveryDate;

    private Long lastUpdated;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Destination destination;
}
