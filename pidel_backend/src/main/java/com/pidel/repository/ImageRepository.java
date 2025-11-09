package com.pidel.repository;

import com.pidel.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByTitle(String name);
}
