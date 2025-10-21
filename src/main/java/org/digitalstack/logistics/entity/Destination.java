package org.digitalstack.logistics.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "destinations")
@Getter
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Integer distance;

}
