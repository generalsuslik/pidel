package com.pidel.dto;

import com.pidel.entity.Ingredient;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PizzaDto {
    private String name;
    private String description;
    private Long pizzaSizeId;
    private Double price;
    List<Ingredient> ingredients;
    private Double kcal;
    private Double protein;
    private Double fat;
    private MultipartFile imageFile;
}
