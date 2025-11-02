package com.pidel.controller;

import com.pidel.entity.Ingredient;
import com.pidel.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@AllArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    @Operation(
            summary = "Get list of all ingredients"
    )
    public List<Ingredient> getAllIngredients() {
        return ingredientService.findAll();
    }

    @GetMapping("/{ingredientId}")
    @Operation(
            summary = "Get ingredient with id = ingredientId"
    )
    public Ingredient getIngredient(@PathVariable("ingredientId") Long ingredientId) {
        return ingredientService.findById(ingredientId);
    }

    @PostMapping
    @Operation(
            summary = "Creates ingredient"
    )
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    @PostMapping("/update/{ingredientId}")
    @Operation(
            summary = "Updates ingredient"
    )
    public Ingredient updateIngredient(@PathVariable("ingredientId") Long ingredientId, @RequestBody Ingredient ingredient) {
        return ingredientService.updateIngredient(ingredientId, ingredient);
    }

    @PostMapping("/delete/{ingredientId}")
    @Operation(
            summary = "Deletes ingredient with id = {ingredientId}"
    )
    public void deleteIngredient(@PathVariable("ingredientId") Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
    }
}
