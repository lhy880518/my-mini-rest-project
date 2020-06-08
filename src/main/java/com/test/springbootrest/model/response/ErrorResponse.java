package com.test.springbootrest.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ErrorResponse {
    private final String status;
    private final int statusCode;
    private final String message;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.getReasonPhrase();
        this.statusCode = status.value();
        this.message = Optional.ofNullable(message).orElse(status.getReasonPhrase());
    }
}
