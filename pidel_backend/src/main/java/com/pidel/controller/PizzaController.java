package com.pidel.controller;

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

    private PizzaService pizzaService;

    @GetMapping
    @Operation(
            summary = "Return list of all pizzas"
    )
    public List<Pizza> getAllPizzas() {
        return pizzaService.findAll();
    }

    @GetMapping("/{pizzaId}")
    @Operation(
            summary = "Return pizza with id = pizzaId"
    )
    public Pizza getPizza(@PathVariable("pizzaId") Long pizzaId) {
        return pizzaService.findById(pizzaId);
    }

    @PostMapping
    @Operation(
            summary = "Creates pizza"
    )
    public Pizza createPizza(@RequestBody Pizza pizza) {
        return pizzaService.createPizza(pizza);
    }

    @PostMapping("/update/{pizzaId}")
    @Operation(
            summary = "Updates pizza"
    )
    public Pizza updatePizza(@PathVariable("pizzaId") Long pizzaId, @RequestBody Pizza pizza) {
        return pizzaService.updatePizza(pizzaId, pizza);
    }

    @PostMapping("/delete/{pizzaId}")
    @Operation(
            summary = "Deletes pizza with id = {pizzaId}"
    )
    public void deletePizza(@PathVariable("pizzaId") Long pizzaId) {
        pizzaService.deletePizza(pizzaId);
    }
}
