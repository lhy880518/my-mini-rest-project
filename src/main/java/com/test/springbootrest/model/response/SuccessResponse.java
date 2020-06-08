package com.test.springbootrest.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class SuccessResponse {
    private final String status;
    private final int statusCode;
    private final Object data;

    public SuccessResponse(Object data) {
        this.status = HttpStatus.OK.getReasonPhrase();
        this.statusCode = HttpStatus.OK.value();
        this.data = data;
    }

}
