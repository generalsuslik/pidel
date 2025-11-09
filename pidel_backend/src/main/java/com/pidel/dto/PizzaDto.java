package com.pidel.dto;

import com.pidel.entity.Ingredient;
import lombok.Data;

import java.util.List;

@Data
public class PizzaDto {
    Long id; // Used only in update when updatable pizza not found
    private String name;
    private String description;
    private Long pizzaSizeId;
    private Double price;
    List<Ingredient> ingredients;
    private Double kcal;
    private Double protein;
    private Double fat;
}
