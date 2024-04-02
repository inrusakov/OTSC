package com.otsc.backend.services;

import com.otsc.backend.dtos.PhotoDto;
import com.otsc.backend.exceptions.AppException;
import com.otsc.backend.entities.Photo;
import com.otsc.backend.mappers.PhotoMapper;
import com.otsc.backend.repositories.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class PhotoService {

    private final PhotoRepository photoRepo;
    private final PhotoMapper photoMapper;

    public String addPhoto(String title, MultipartFile file) throws IOException {
        if (!isImage(file)) {
            throw new AppException("File is not an image", HttpStatus.BAD_REQUEST);
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new AppException("File size is too large", HttpStatus.BAD_REQUEST);
        }
        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                throw new AppException("Invalid file content", HttpStatus.BAD_REQUEST);
            }
        }

        Photo photo = new Photo();
        photo.setTitle(title);
        photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepo.insert(photo);

        return photo.getId();
    }

    private boolean isImage(MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            return image != null;
        } catch (IOException e) {
            throw new AppException("Bad image format", HttpStatus.BAD_REQUEST);
        }
    }

    public PhotoDto getPhoto(String id) {
        if (photoRepo.findById(id).isEmpty()) {
            throw new AppException("No image found", HttpStatus.NOT_FOUND);
        }
        return photoMapper.photoToPhotoDto(photoRepo.findById(id).get());
    }
}
