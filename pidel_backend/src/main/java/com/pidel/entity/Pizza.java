package com.pidel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pizza_size_id", referencedColumnName = "id")
    private PizzaSize pizzaSize;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Ingredient> ingredients;

    @Column(name = "kcal", nullable = false)
    private Double kcal;

    @Column(name = "protein", nullable = false)
    private Double protein;

    @Column(name = "fat", nullable = false)
    private Double fat;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "pizza_image_id", referencedColumnName = "id")
    private Image image;
}
