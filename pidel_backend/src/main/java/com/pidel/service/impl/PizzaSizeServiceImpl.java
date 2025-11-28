package com.pidel.service.impl;

import com.pidel.entity.PizzaSize;
import com.pidel.repository.PizzaSizeRepository;
import com.pidel.service.PizzaSizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PizzaSizeServiceImpl implements PizzaSizeService {
    private final PizzaSizeRepository pizzaSizeRepository;

    @Override
    public List<PizzaSize> findAll() {
        return pizzaSizeRepository.findAll();
    }

    @Override
    public PizzaSize findById(Long id) {
        return pizzaSizeRepository
                .findById(id)
                .orElseThrow();
    }

    @Override
    public Boolean exists(Integer size) {
        return pizzaSizeRepository.findBySize(size).isPresent();
    }

    @Override
    public PizzaSize findBySize(Integer size) {
        return pizzaSizeRepository
                .findBySize(size)
                .orElseThrow(() -> new RuntimeException("Failed to find pizzaSize = %d not found".formatted(size)));
    }

    @Override
    public List<PizzaSize> findBySizes(List<Integer> sizes) {
        return sizes.stream()
                .map(this::findBySize)
                .collect(Collectors.toList());
    }
}
