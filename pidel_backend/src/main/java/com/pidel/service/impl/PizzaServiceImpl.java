package com.pidel.service.impl;

import com.pidel.entity.Pizza;
import com.pidel.entity.PizzaSize;
import com.pidel.repository.PizzaRepository;
import com.pidel.repository.PizzaSizeRepository;
import com.pidel.service.PizzaService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PizzaServiceImpl implements PizzaService {

    PizzaRepository pizzaRepository;
    PizzaSizeRepository pizzaSizeRepository;

    @Override
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    @Override
    public Pizza findById(Long id) {
        return pizzaRepository
                .findById(id)
                .orElseThrow();
    }

    @Override
    public Pizza createPizza(Pizza pizza) {
        if (pizza.getPizzaSize() == null || pizza.getPizzaSize().getId() == null) {
            throw new RuntimeException("PizzaSize is required");
        }

        PizzaSize pizzaSize = pizzaSizeRepository.findById(pizza.getPizzaSize().getId())
                .orElseThrow();

        pizza.setPizzaSize(null);
        pizza.setPizzaSize(pizzaSize);
        return pizzaRepository.save(pizza);
    }

    @Override
    @Transactional
    public Pizza updatePizza(Long id, Pizza pizza) {
        return pizzaRepository.findById(id)
                .map(pizzaToUpdate -> {
                    pizzaToUpdate.setName(pizza.getName());
                    pizzaToUpdate.setDescription(pizza.getDescription());
                    pizzaToUpdate.setPrice(pizza.getPrice());
                    pizzaToUpdate.setPizzaSize(pizza.getPizzaSize());
                    pizzaToUpdate.setIngredients(pizza.getIngredients());
                    pizzaToUpdate.setFat(pizza.getFat());
                    pizzaToUpdate.setKcal(pizza.getKcal());
                    pizzaToUpdate.setProtein(pizza.getProtein());
                    return pizzaRepository.save(pizzaToUpdate);
                })
                .orElseGet(() -> {
                    pizza.setId(id);
                    return createPizza(pizza);
                });
    }

    @Override
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }
}
