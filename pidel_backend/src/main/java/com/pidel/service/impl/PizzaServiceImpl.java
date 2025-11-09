package com.pidel.service.impl;

import com.pidel.dto.PizzaDto;
import com.pidel.entity.Pizza;
import com.pidel.repository.PizzaRepository;
import com.pidel.service.PizzaService;
import com.pidel.service.PizzaSizeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PizzaServiceImpl implements PizzaService {

    PizzaRepository pizzaRepository;
    PizzaSizeService pizzaSizeService;

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
    public Pizza createPizza(@NonNull PizzaDto request) {
        if (request.getPizzaSizeId() == null || !pizzaSizeService.exists(request.getPizzaSizeId())) {
            throw new RuntimeException("PizzaSize is required");
        }

        var pizza = Pizza.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .price(request.getPrice())
                        .kcal(request.getKcal())
                        .protein(request.getProtein())
                        .fat(request.getFat())
                        .pizzaSize(pizzaSizeService.findById(request.getPizzaSizeId()))
                        .build();
        return pizzaRepository.save(pizza);
    }

    @Override
    @Transactional
    public Pizza updatePizza(Long id, @NonNull PizzaDto request) {
        return pizzaRepository.findById(id)
                .map(pizzaToUpdate -> {
                    pizzaToUpdate.setName(request.getName().isEmpty() ? pizzaToUpdate.getName() : request.getName());
                    pizzaToUpdate.setDescription(request.getDescription().isEmpty() ? pizzaToUpdate.getDescription() : request.getDescription());
                    pizzaToUpdate.setPrice(request.getPrice().isNaN() ? pizzaToUpdate.getPrice() : request.getPrice());
                    pizzaToUpdate.setPizzaSize(pizzaSizeService.findById(request.getPizzaSizeId()));
                    pizzaToUpdate.setIngredients(request.getIngredients().isEmpty() ? pizzaToUpdate.getIngredients() : request.getIngredients());
                    pizzaToUpdate.setFat(request.getFat().isNaN() ? pizzaToUpdate.getFat() : request.getFat());
                    pizzaToUpdate.setKcal(request.getKcal().isNaN() ? pizzaToUpdate.getKcal() : request.getKcal());
                    pizzaToUpdate.setProtein(request.getProtein().isNaN() ? pizzaToUpdate.getProtein() : request.getProtein());
                    return pizzaRepository.save(pizzaToUpdate);
                })
                .orElseGet(() -> {
                    request.setId(id);
                    return createPizza(request);
                });
    }

    @Override
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }
}
