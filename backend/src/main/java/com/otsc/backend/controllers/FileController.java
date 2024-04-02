package com.otsc.backend.controllers;

import com.otsc.backend.dtos.PhotoDto;
import com.otsc.backend.entities.Photo;
import com.otsc.backend.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class FileController {

    private final PhotoService photoService;

    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam("title") String title,
                           @RequestParam("image") MultipartFile image)
            throws IOException {
        String id = photoService.addPhoto(title, image);
        return id;
    }

    @GetMapping("/photos/{id}")
    public ResponseEntity<PhotoDto> getPhoto(@PathVariable String id) {
        PhotoDto photo = photoService.getPhoto(id);
        return new ResponseEntity<>(photo, HttpStatus.OK);
    }
}
