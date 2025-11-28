package com.pidel.repository;

import com.pidel.entity.PizzaSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PizzaSizeRepository extends JpaRepository<PizzaSize, Long> {
    Optional<PizzaSize> findBySize(Integer size);
}
