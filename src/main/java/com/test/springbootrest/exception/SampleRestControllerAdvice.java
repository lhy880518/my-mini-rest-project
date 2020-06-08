package com.test.springbootrest.exception;

import com.test.springbootrest.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class SampleRestControllerAdvice {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> FileUploadException(FileUploadException exception){
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponse> invalidParams(HttpServletRequest req, InvalidParameterException e) {
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> SqlException(Exception exception){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 서버 오류 입니다. 잠시 후 다시 시도해 주세요."));
    }
}
