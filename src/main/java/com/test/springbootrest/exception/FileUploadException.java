package com.test.springbootrest.exception;

public class FileUploadException extends RuntimeException{
    public FileUploadException(String message){
        super(message);
    }
}
