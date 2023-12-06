package com.yash.app2container.controller;

import com.yash.app2container.service.FileService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

@RestController

public class FileController {

    @Resource
    private FileService service;

    @GetMapping("/uploadFile")
    public String uploadFile(){
        return "working ";
    }

    @GetMapping("/createBucket")
    public boolean createBucket(@RequestParam String bucketName){
        System.out.println("creating bucket " + bucketName);
        CreateBucketResponse response = service.createBucket(bucketName);
        return response.sdkHttpResponse().isSuccessful();
    }

    @PostMapping("/upload")
    public ResponseEntity<Boolean> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        PutObjectResponse response = null;
        try {
             response = service.uploadFile(file);

        } catch (IOException e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(response.sdkHttpResponse().isSuccessful(), HttpStatus.OK);
    }

}
