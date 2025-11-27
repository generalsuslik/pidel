package com.pidel.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PizzaDto {
    private String name;
    private String description;
    private Long pizzaSizeId;
    private Double price;
    private List<IngredientDto> ingredients;
    private Double kcal;
    private Double protein;
    private Double fat;

    @JsonIgnore
    private MultipartFile imageFile;
}
