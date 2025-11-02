package com.pidel.service.impl;

import com.pidel.entity.Ingredient;
import com.pidel.repository.IngredientRepository;
import com.pidel.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient findById(Long id) {
        return ingredientRepository
                .findById(id)
                .orElseThrow();
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        return ingredientRepository.findById(id)
                .map(ingredientToUpdate -> {
                    ingredientToUpdate.setName(ingredient.getName());
                    return ingredientRepository.save(ingredientToUpdate);
                })
                .orElseGet(() -> {
                    ingredient.setId(id);
                    return createIngredient(ingredient);
                });
    }

    @Override
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}
