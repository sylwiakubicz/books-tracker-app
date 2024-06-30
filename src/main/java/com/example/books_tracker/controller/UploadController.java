package com.example.books_tracker.controller;

import com.example.books_tracker.model.UploadResponse;
import com.example.books_tracker.service.UploadService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class UploadController {
    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping("/upload")
    public Object handleUpload(@RequestParam("image") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return "File is empty";
        }
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        UploadResponse uploadResponse = uploadService.uploadImageToDrive(tempFile);
        System.out.println(uploadResponse.getUrl());
        return uploadResponse;
    }
}
