package com.pidel.controller;

import com.pidel.dto.PizzaDto;
import com.pidel.entity.Pizza;
import com.pidel.service.PizzaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pizza")
@AllArgsConstructor
public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping
    @Operation(
            summary = "Returns list of all pizzas"
    )
    public List<Pizza> getAllPizzas() {
        return pizzaService.findAll();
    }

    @GetMapping("/{pizzaId}")
    @Operation(
            summary = "Returns pizza with id = pizzaId"
    )
    public Pizza getPizza(@PathVariable("pizzaId") Long pizzaId) {
        return pizzaService.findById(pizzaId);
    }

    @PostMapping
    @Operation(
            summary = "Creates pizza"
    )
    public Pizza createPizza(@RequestBody PizzaDto request) {
        return pizzaService.createPizza(request);
    }

    @PostMapping("/update/{pizzaId}")
    @Operation(
            summary = "Updates pizza"
    )
    public Pizza updatePizza(@PathVariable("pizzaId") Long pizzaId, @RequestBody PizzaDto request) {
        return pizzaService.updatePizza(pizzaId, request);
    }

    @PostMapping("/delete/{pizzaId}")
    @Operation(
            summary = "Deletes pizza with id = {pizzaId}"
    )
    public void deletePizza(@PathVariable("pizzaId") Long pizzaId) {
        pizzaService.deletePizza(pizzaId);
    }
}
