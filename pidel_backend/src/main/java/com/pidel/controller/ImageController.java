package com.pidel.controller;

import com.pidel.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/{pizzaTitle}")
    public ResponseEntity<?> createImage(@PathVariable String pizzaTitle, @RequestParam("image") MultipartFile imageFile) throws IOException {
        var image = imageService.saveImageToStorage(pizzaTitle, imageFile, "pizzas");
        return ResponseEntity.ok(image);
    }

    @GetMapping("/{imageTitle}")
    public ResponseEntity<?> getImage(@PathVariable String imageTitle) throws IOException {
        byte[] imageBytes = imageService.getImage(imageTitle);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.valueOf(imageService.getImageData(imageTitle).getType()))
                .body(imageBytes);
    }

    @GetMapping("/data/{imageTitle}")
    public ResponseEntity<?> getImageData(@PathVariable String imageTitle) {
        return ResponseEntity.ok(imageService.getImageData(imageTitle));
    }
}
