package com.shipmonk.testingday.controller;

import com.shipmonk.testingday.model.dtos.RateDto;
import com.shipmonk.testingday.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.joda.money.CurrencyUnit;

import java.time.LocalDate;

@RestController
@RequestMapping(
    path = "/api/v1/rates"
)
//todo: config openapi
@RequiredArgsConstructor
public class ExchangeRatesController{

    final RateService rateService;

    @GetMapping(value = {"/{day}", "/{day}/{currency}"})
    public ResponseEntity<RateDto> getRates(@PathVariable String day, @PathVariable(required = false) CurrencyUnit currency)
    {
        if (currency == null) {
            currency = CurrencyUnit.USD;
        }

        return ResponseEntity.ok().body(rateService.getRates(LocalDate.parse(day), currency));
    }
}
