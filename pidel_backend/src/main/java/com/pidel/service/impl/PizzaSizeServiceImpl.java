package com.pidel.service.impl;

import com.pidel.entity.PizzaSize;
import com.pidel.repository.PizzaSizeRepository;
import com.pidel.service.PizzaSizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PizzaSizeServiceImpl implements PizzaSizeService {
    private final PizzaSizeRepository pizzaSizeRepository;

    @Override
    public List<PizzaSize> findAll() {
        return pizzaSizeRepository.findAll();
    }

    @Override
    public Boolean exists(Long id) {
        return pizzaSizeRepository.findById(id).isPresent();
    }

    @Override
    public PizzaSize findById(Long id) {
        return pizzaSizeRepository
                .findById(id)
                .orElseThrow();
    }
}
