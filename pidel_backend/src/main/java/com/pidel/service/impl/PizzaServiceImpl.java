package com.pidel.service.impl;

import com.pidel.dto.PizzaDto;
import com.pidel.entity.Pizza;
import com.pidel.repository.PizzaRepository;
import com.pidel.service.ImageService;
import com.pidel.service.PizzaService;
import com.pidel.service.PizzaSizeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class PizzaServiceImpl implements PizzaService {

    PizzaRepository pizzaRepository;
    PizzaSizeService pizzaSizeService;
    ImageService imageService;

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
    public Pizza createPizza(@NonNull PizzaDto request) throws IOException {
        if (request.getPizzaSizeId() == null || !pizzaSizeService.exists(request.getPizzaSizeId())) {
            throw new RuntimeException("PizzaSize is required");
        }

        var pizzaBuilder = Pizza.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .kcal(request.getKcal())
                .protein(request.getProtein())
                .fat(request.getFat())
                .pizzaSize(pizzaSizeService.findById(request.getPizzaSizeId()));

        if (request.getImageFile() == null) {
            pizzaBuilder.image(imageService.getDefaultImageData());
        } else {
            var image = imageService.saveImageToStorage(request.getImageFile(), "/pizza");
            pizzaBuilder.image(image);
        }

        return pizzaRepository.save(pizzaBuilder.build());
    }

    @Override
    @Transactional
    public Pizza updatePizza(Long id, @NonNull PizzaDto request) {
        return pizzaRepository.findById(id)
                .map(pizzaToUpdate -> {
                    pizzaToUpdate.setName(request.getName() == null ? pizzaToUpdate.getName() : request.getName());
                    pizzaToUpdate.setDescription(request.getDescription() == null ? pizzaToUpdate.getDescription() : request.getDescription());
                    pizzaToUpdate.setPrice(request.getPrice() == null ? pizzaToUpdate.getPrice() : request.getPrice());
                    pizzaToUpdate.setPizzaSize(request.getPizzaSizeId() == null ? pizzaToUpdate.getPizzaSize() : pizzaSizeService.findById(request.getPizzaSizeId()));
                    try {
                        pizzaToUpdate.setImage(request.getImageFile() == null ? pizzaToUpdate.getImage() : imageService.saveImageToStorage(request.getImageFile(), "/pizza"));
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save image for update");
                    }
                    pizzaToUpdate.setIngredients(request.getIngredients() == null ? pizzaToUpdate.getIngredients() : request.getIngredients());
                    pizzaToUpdate.setFat(request.getFat() == null ? pizzaToUpdate.getFat() : request.getFat());
                    pizzaToUpdate.setKcal(request.getKcal() == null ? pizzaToUpdate.getKcal() : request.getKcal());
                    pizzaToUpdate.setProtein(request.getProtein() == null ? pizzaToUpdate.getProtein() : request.getProtein());
                    return pizzaRepository.save(pizzaToUpdate);
                })
                .orElseGet(() -> {
                    try {
                        return createPizza(request);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to create pizza");
                    }
                });
    }

    @Override
    public void deletePizza(Long id) {
        pizzaRepository.deleteById(id);
    }
}
