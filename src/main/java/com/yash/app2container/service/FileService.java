package com.yash.app2container.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.*;

@Service
public class FileService {

    @Resource
    private S3Client client;


    public CreateBucketResponse createBucket(String bucketName){
        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();

        return client.createBucket(createBucketRequest);
    }

    public PutObjectResponse uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        byte[] bytes = file.getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("poc-for-app-2-container")
                .key(fileName)
                .build();

        return client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, bytes.length));
    }


}
