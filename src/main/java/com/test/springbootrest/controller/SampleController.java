package com.test.springbootrest.controller;

import com.test.springbootrest.exception.HandleBindingResult;
import com.test.springbootrest.exception.InvalidParameterException;
import com.test.springbootrest.model.request.RequestSample;
import com.test.springbootrest.model.request.RequestSampleEndpoint;
import com.test.springbootrest.model.response.ErrorResponse;
import com.test.springbootrest.model.response.SuccessResponse;
import com.test.springbootrest.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;

@RequestMapping(value = "/sample")
@RestController
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @HandleBindingResult(InvalidParameterException.class)
    @PostMapping(value = "/upload.do")
    public ResponseEntity<SuccessResponse> feedDailyLifeReg(
            @Valid RequestSample requestSample,
            BindingResult bindingResult
            ) {
        sampleService.uploadFiles(requestSample.getFile());
        sampleService.addFeed(requestSample);
        return ResponseEntity.ok(new SuccessResponse(requestSample));
    }

    @HandleBindingResult(InvalidParameterException.class)
    @PostMapping(value = "/endpointCheck.do")
    public ResponseEntity<SuccessResponse> endpointCheck(
            @Valid RequestSampleEndpoint requestSampleEndpoint,
            BindingResult bindingResult
    ) {
        sampleService.endpointChecker(requestSampleEndpoint);
        return ResponseEntity.ok(new SuccessResponse(requestSampleEndpoint));
    }
}
