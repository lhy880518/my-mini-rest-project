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
public class RequestSample{

    @NotEmpty(message = "이름은 반드시 넣어야해요")
    @Length(max = 3, message = "세글자까지만 입력 가능해요")
    private String name;

    @JsonIgnore
    private MultipartFile[] file;
}
