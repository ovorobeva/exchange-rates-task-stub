package com.shipmonk.testingday.service;

import com.shipmonk.testingday.model.dtos.RateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface RateService {
    ResponseEntity<RateDto> getRates(@Valid LocalDate day, @Pattern(regexp = "[a-zA-Z][a-zA-Z][a-zA-Z]") String currency);
}
