package com.example.s3practice.upload;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "s3")
@RequiredArgsConstructor
public class S3Properties {

    private final String accessKey;
    private final String secretKey;
    private final String bucketName;
    private final String region;
}
