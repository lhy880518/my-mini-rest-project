package com.test.springbootrest.service.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;
import com.test.springbootrest.model.entity.FeedDailyLife;
import com.test.springbootrest.model.entity.FeedDailyLifeRepository;
import com.test.springbootrest.model.request.RequestSample;
import com.test.springbootrest.model.request.RequestSampleEndpoint;
import com.test.springbootrest.service.SampleService;
import com.test.springbootrest.util.upload.Uploader;
import com.test.springbootrest.util.upload.implementation.AmazonConcept;
import com.test.springbootrest.util.uploadPathMaker.PathDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SampleServiceImpl implements SampleService {

    @Autowired
    private FeedDailyLifeRepository feedDailyLifeRepository;

    @Autowired
    @Qualifier("amazonConcept")
    private Uploader uploader;

    @Override
    @Transactional
    public void addFeed(RequestSample requestSample) {
        feedDailyLifeRepository.save(new FeedDailyLife(requestSample.getName()));
    }

    @Override
    public void uploadFiles(MultipartFile[] file) {
        uploader.upload(PathDefinition.AWS_THUMBNAIL.getFullFilePath(), file);
    }

    @Override
    public void uploadFiles(File file) {
        uploader.upload(PathDefinition.AWS_THUMBNAIL.getFullFilePath(), file);
    }

    @Override
    public void endpointChecker(RequestSampleEndpoint requestSampleEndpoint) {
    }
}
