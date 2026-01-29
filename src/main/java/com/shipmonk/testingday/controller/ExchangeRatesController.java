package com.shipmonk.testingday.controller;

import com.shipmonk.testingday.model.dtos.RateDto;
import com.shipmonk.testingday.service.RateService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
//todo: create openapi and readme documentation

@RestController
@RequestMapping(
    path = "/api/v1/rates"
)
@RequiredArgsConstructor
public class ExchangeRatesController{

    final private RateService rateService;

    @GetMapping(value = {"/{day}", "/{day}/{currency}"})
    public ResponseEntity<RateDto> getRates(@PathVariable String day,
                                            @RequestParam(required = false)
                                            @Pattern(regexp = "[a-zA-Z][a-zA-Z][a-zA-Z]") String currency)
    {
        if (currency == null) {
            currency = "USD";
        }

        return rateService.getRates(LocalDate.parse(day), currency);
    }
}
