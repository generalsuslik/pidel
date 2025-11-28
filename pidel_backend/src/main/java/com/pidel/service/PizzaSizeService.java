package com.pidel.service;

import com.pidel.entity.PizzaSize;

import java.util.List;

public interface PizzaSizeService {
    List<PizzaSize> findAll();
    PizzaSize findById(Long id);
    Boolean exists(Integer size);
    PizzaSize findBySize(Integer size);
    List<PizzaSize> findBySizes(List<Integer> sizes);
}
