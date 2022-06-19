package com.example.s3practice.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping("/images/upload")
    public String upload(MultipartFile multipartFile) throws IOException {
        uploadService.upload(multipartFile);
        return "url";
    }

}
