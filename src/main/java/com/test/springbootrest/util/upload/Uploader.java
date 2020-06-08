package com.test.springbootrest.util.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface Uploader {
    void upload(String path, File file);
    void upload(String path, MultipartFile[] files);
}
