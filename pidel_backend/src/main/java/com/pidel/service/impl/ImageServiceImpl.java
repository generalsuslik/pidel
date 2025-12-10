package com.pidel.service.impl;

import com.pidel.entity.Image;
import com.pidel.repository.ImageRepository;
import com.pidel.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("${file.image.pizza.path}")
    private String IMAGE_FILE_PATH;

    @Value("${http.url}")
    private String BASE_URL;

    private final ImageRepository imageRepository;

    @Override
    public Image saveImageToStorage(String pizzaTitle, MultipartFile imageFile, String service) throws IOException {
        boolean isDefault = Objects.equals(pizzaTitle, "default");

        UUID uuidPart = UUID.randomUUID();
        String imageTitle = uuidPart + "_" + imageFile.getOriginalFilename();
        String imageDirectory = IMAGE_FILE_PATH + (isDefault ? "/default/" : "/") + service;
        String imagePath =  imageDirectory + "/ " + imageTitle;
        String imageUrl = BASE_URL + "/images" + (isDefault ? "/default/" : "/") + imageTitle;

        var image = Image.builder()
                .title(imageTitle)
                .pizzaName(pizzaTitle)
                .type(imageFile.getContentType())
                .imagePath(imagePath)
                .imageUrl(imageUrl)
                .createdAt(new Date())
                .isDefault(isDefault)
                .build();

        Path imageDir = Path.of(imageDirectory);
        if (Files.notExists(imageDir)) {
            Files.createDirectories(imageDir);
        }
        imageFile.transferTo(Path.of(imagePath));
        imageRepository.save(image);

        return image;
    }

    @Override
    public Image getImageData(String imageTitle) {
        return imageRepository.findByTitle(imageTitle);
    }

    @Override
    public Image getImageDataById(Long imageId) {
        return imageRepository.findById(imageId).orElse(null);
    }

    @Override
    public Image getDefaultImageData() {
        return getImageData("29f41483-257a-40b3-aaac-d3bdfdafb50d_default_image.png");
    }

    @Override
    public byte[] getImage(String imageName) throws IOException {
        Image image = getImageData(imageName);
        String imagePath = image.getImagePath();
        log.info("Requested image path: {}", imagePath);
        return Files.readAllBytes(Path.of(imagePath));
    }

    @Override
    public void deleteImage(String imageTitle) throws IOException {
        Image image = getImageData(imageTitle);
        Path imagePath = Path.of(image.getImagePath());
        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
            imageRepository.delete(image);
        }
    }
}
