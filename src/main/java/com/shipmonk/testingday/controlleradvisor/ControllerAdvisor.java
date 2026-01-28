package com.shipmonk.testingday.controlleradvisor;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignExceptions(FeignException ex) {
        return ResponseEntity.status(ex.status()).body(ex.getMessage());
    }
}
