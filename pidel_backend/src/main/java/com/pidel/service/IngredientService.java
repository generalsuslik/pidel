package com.pidel.service;

import com.pidel.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll();
    Ingredient findById(Long id);
    Ingredient createIngredient(Ingredient ingredient);
    Ingredient updateIngredient(Long id, Ingredient ingredient);
    void deleteIngredient(Long id);
}
