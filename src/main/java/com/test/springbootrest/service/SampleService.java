package com.test.springbootrest.service;

import com.test.springbootrest.model.request.RequestSample;
import com.test.springbootrest.model.request.RequestSampleEndpoint;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface SampleService {
    void addFeed(RequestSample requestSample);

    void uploadFiles(MultipartFile[] file);

    void uploadFiles(File file);

    void endpointChecker(RequestSampleEndpoint requestSampleEndpoint);
}
