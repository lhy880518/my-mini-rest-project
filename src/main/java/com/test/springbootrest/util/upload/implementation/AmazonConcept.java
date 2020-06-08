package com.test.springbootrest.util.upload.implementation;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.test.springbootrest.exception.FileUploadException;
import com.test.springbootrest.util.upload.Uploader;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Component
public class AmazonConcept implements Uploader {

    private AmazonS3 amazonS3;

    public AmazonConcept(@Value("${amazon.access-key}")String accessKey,
                         @Value("${amazon.secret-key}")String secretKey
    ){
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey,secretKey);
        amazonS3 = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }

    @Override
    public void upload(String path, File file) {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(path, file.getName(), file);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
            amazonS3.getUrl(path, file.getName());
        }catch (AmazonServiceException e){
            throw new FileUploadException("AmazonServiceException="+e);
        }
    }

    @Override
    public void upload(String path, MultipartFile[] files){
        try {
            for(MultipartFile multipartFile : files){

                File uploadFile = convert(multipartFile).orElseThrow(() -> new FileUploadException("File Convert Fail"));

                PutObjectRequest putObjectRequest = new PutObjectRequest(path, uploadFile.getName(), uploadFile);
                putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
                amazonS3.putObject(putObjectRequest);
                uploadFile.delete();
            }
        }catch (Exception e){
            throw new FileUploadException("AmazonServiceException="+e);
        }
    }

    private Optional<File> convert(MultipartFile file){
        File convertFile = new File(file.getOriginalFilename());
        try {
            if(convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            throw new FileUploadException("AmazonServiceException="+e);
        }

        return Optional.empty();
    }
}
