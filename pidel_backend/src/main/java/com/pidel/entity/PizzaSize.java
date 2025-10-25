package com.pidel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pizza_size")
public class PizzaSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // size in centimeters
    @Column(name = "size", nullable = false, unique = true)
    private Integer size;

    @OneToMany(mappedBy = "pizzaSize", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pizza> pizzas = new ArrayList<>();
}
