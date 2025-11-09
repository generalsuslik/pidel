package com.pidel.service;

import com.pidel.dto.PizzaDto;
import com.pidel.entity.Pizza;

import java.util.List;

public interface PizzaService {
    List<Pizza> findAll();
    Pizza findById(Long id);
    Pizza createPizza(PizzaDto request);
    Pizza updatePizza(Long id, PizzaDto request);
    void deletePizza(Long id);
}
