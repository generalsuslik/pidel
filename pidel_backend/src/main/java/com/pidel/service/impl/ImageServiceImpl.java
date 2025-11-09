package com.pidel.service.impl;

import com.pidel.entity.Image;
import com.pidel.repository.ImageRepository;
import com.pidel.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value("${file.image.pizza.path}")
    private String IMAGE_FILE_PATH;

    @Value("${http.url}")
    private String BASE_URL;

    private final ImageRepository imageRepository;

    @Override
    public Image saveImageToStorage(MultipartFile imageFile, String service) throws IOException {
        Path dir = Path.of(IMAGE_FILE_PATH + service + "/");
        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        }

        UUID uuidPart = UUID.randomUUID();
        String imageTitle = uuidPart + "_" + imageFile.getOriginalFilename();
        String imagePath = IMAGE_FILE_PATH + service + "/" + imageTitle;
        String imageUrl = BASE_URL + "/images/" + imageTitle;

        var image = Image.builder()
                .title(imageTitle)
                .type(imageFile.getContentType())
                .imagePath(imagePath)
                .imageUrl(imageUrl)
                .createdAt(new Date())
                .build();
        imageRepository.save(image);
        imageFile.transferTo(Path.of(imagePath));

        return image;
    }

    @Override
    public Image saveImageToStorage(MultipartFile imageFile, String service, boolean isDefault) throws IOException {
        String imageTitle = imageFile.getOriginalFilename();
        String imagePath = IMAGE_FILE_PATH + "default/" + service + "/" + imageTitle;
        String imageUrl = BASE_URL + "/images/default/" + imageTitle;

        var image = Image.builder()
                .title(imageTitle)
                .type(imageFile.getContentType())
                .imagePath(imagePath)
                .imageUrl(imageUrl)
                .createdAt(new Date())
                .isDefault(isDefault)
                .build();
        imageRepository.save(image);
        imageFile.transferTo(Path.of(imagePath));

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
