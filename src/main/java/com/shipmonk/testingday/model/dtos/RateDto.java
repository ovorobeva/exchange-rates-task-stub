package com.shipmonk.testingday.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class RateDto {

    private boolean success;

    @NotBlank
    private String date;

    @NotEmpty
    private Map<String, Double> rates;
}
