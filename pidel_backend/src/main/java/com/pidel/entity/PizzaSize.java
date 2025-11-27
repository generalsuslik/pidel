package com.pidel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "pizzaSize", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pizza> pizzas = new ArrayList<>();
}
