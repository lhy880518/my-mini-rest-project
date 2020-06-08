package com.test.springbootrest.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
public class RequestSampleEndpoint {

    @NotEmpty(message = "endpoint는 필수 입니다.")
    private String endpoint;
}
