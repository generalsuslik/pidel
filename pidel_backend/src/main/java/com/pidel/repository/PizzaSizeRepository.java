package com.pidel.repository;

import com.pidel.entity.PizzaSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaSizeRepository extends JpaRepository<PizzaSize, Long> {
}
