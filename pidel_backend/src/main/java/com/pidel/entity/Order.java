package com.pidel.entity;

import com.pidel.entity.common.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private Double total;

    private List<Pizza> pizzas;
}
