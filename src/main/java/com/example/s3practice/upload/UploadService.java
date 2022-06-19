package com.example.s3practice.upload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final AmazonS3 amazonS3;
    private final S3Properties s3Properties;

    public String uploadPublic(MultipartFile multipartFile) {
        String key = generateUniqueKey(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        amazonS3.putObject(generateObjectRequest(multipartFile, key)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return getUrl(key);
    }

    private String getUrl(String key) {
        return amazonS3.getUrl(s3Properties.getBucketName(), key).toString();
    }

    private String generateUniqueKey(String fileName) {
        String fileNameExtension = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID() + fileNameExtension;
    }

    private PutObjectRequest generateObjectRequest(MultipartFile multipartFile, String key) {
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("file이 올바르지 않습니다.");
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        return new PutObjectRequest(s3Properties.getBucketName(), key,
                inputStream, objectMetadata);
    }

}
