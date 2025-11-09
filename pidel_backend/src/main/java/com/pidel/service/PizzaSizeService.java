package com.pidel.service;

import com.pidel.entity.PizzaSize;

import java.util.List;

public interface PizzaSizeService {
    List<PizzaSize> findAll();
    Boolean exists(Long id);
    PizzaSize findById(Long id);
}
