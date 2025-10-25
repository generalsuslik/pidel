package com.pidel.service;

import com.pidel.entity.Pizza;

import java.util.List;

public interface PizzaService {
    List<Pizza> findAll();
    Pizza findById(Long id);
    Pizza createPizza(Pizza pizza);
    Pizza updatePizza(Long id, Pizza pizza);
    void deletePizza(Long id);
}
