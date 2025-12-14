package com.pidel.service;

import java.util.Optional;

import com.pidel.dto.PizzaDto;
import com.pidel.entity.Ingredient;
import com.pidel.entity.PizzaSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.pidel.entity.Pizza;
import com.pidel.repository.PizzaRepository;
import com.pidel.service.impl.PizzaServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

class PizzaServiceTest {
    @Mock
    private ImageService imageService;
    @Mock
    private PizzaRepository pizzaRepository;
    @Mock
    private PizzaSizeService pizzaSizeService;
    @InjectMocks
    private PizzaServiceImpl pizzaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdReturnsPizza() {
        Pizza pizza = new Pizza();
        pizza.setId(1L);
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        Pizza result = pizzaService.findById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    void testUpdatePizzaReturnsPizza() {
        List<PizzaSize> initPizzaSizes = List.of(
                PizzaSize.builder().id(1L).size(25).build()
        );
        Pizza pizza = Pizza.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(245D)
                .kcal(24.5)
                .fat(12D)
                .protein(5.1)
                .pizzaSizes(initPizzaSizes)
                .ingredients(List.of(Ingredient.builder().id(1L).weight(24.5).name("ingredient").build()))
                .build();

        List<Integer> updatedPizzaSizes = List.of(20, 25, 30, 35);
        List<PizzaSize> updatedPizzaSizesEntity = updatedPizzaSizes.stream()
                .map(size -> PizzaSize.builder().size(size).build())
                .collect(Collectors.toList());
        PizzaDto updatablePizza = PizzaDto.builder()
                    .description("new description")
                    .price(250D)
                    .pizzaSizes(updatedPizzaSizes)
                    .build();

        when(imageService.getDefaultImageData()).thenReturn(null);
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        when(pizzaRepository.save(any(Pizza.class))).thenReturn(Pizza.builder()
                .id(1L)
                .name("name")
                .description(updatablePizza.getDescription())
                .price(updatablePizza.getPrice())
                .kcal(24.5)
                .fat(12D)
                .protein(5.1)
                .pizzaSizes(updatedPizzaSizesEntity)
                .ingredients(List.of(Ingredient.builder().id(1L).weight(24.5).name("ingredient").build()))
                .build()
        );
        when(pizzaSizeService.findBySizes(updatedPizzaSizes)).thenReturn(updatedPizzaSizesEntity);

        Pizza updatedPizza = pizzaService.updatePizza(1L, updatablePizza);
        assertNotNull(updatedPizza);
        assertEquals(pizza.getId(), updatedPizza.getId());
        assertEquals(pizza.getName(), updatedPizza.getName());
        assertEquals("new description", updatedPizza.getDescription());
        assertEquals(250D, updatedPizza.getPrice());
    }
}
