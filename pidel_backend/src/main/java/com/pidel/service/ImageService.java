package com.pidel.service;

import com.pidel.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image saveImageToStorage(String pizzaTitle, MultipartFile imageFile, String service) throws IOException;
    Image getImageData(String imageTitle);
    Image getImageDataById(Long imageId);
    Image getDefaultImageData();
    byte[] getImage(String imageName) throws IOException;
    void deleteImage(String imageTitle) throws IOException;
}
