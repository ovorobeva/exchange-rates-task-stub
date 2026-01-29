package com.shipmonk.testingday.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@EqualsAndHashCode
public class RateDto {

    @NotBlank
    private String date;

    @NotEmpty
    private Map<String, Double> rates;
}
