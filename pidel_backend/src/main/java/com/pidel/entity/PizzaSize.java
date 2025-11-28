package com.pidel.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "pizza_size")
public class PizzaSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // size in centimeters
    @Column(name = "size", nullable = false, unique = true)
    private Integer size;

    @Column(name = "coefficient", nullable = false)
    private Double coefficient;
}
