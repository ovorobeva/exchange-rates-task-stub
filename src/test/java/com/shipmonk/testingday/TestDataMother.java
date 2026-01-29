package com.shipmonk.testingday;

import com.shipmonk.testingday.model.dtos.RateDto;
import com.shipmonk.testingday.model.entities.RateEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TestDataMother {
    private TestDataMother() {
    }

    public static RateDto createRateDto(LocalDate date) {
        RateDto rateDto = new RateDto();
        rateDto.setDate(date.toString());
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("EUR", 0.85);
        rates.put("GBP", 0.75);
        rateDto.setRates(rates);
        return rateDto;
    }


    public static java.util.List<RateEntity> createRateEntityList(LocalDate date) {
        return java.util.List.of(
            createRateEntity("USD", 0.85, date.toString()),
            createRateEntity("EUR", 1.0, date.toString()),
            createRateEntity("GBP", 0.75, date.toString())
        );
    }
    public static RateEntity createRateEntity(String currency, Double rateValue, String date) {
        RateEntity rateEntity = new RateEntity();
        rateEntity.setCurrency(currency);
        rateEntity.setRateValue(rateValue);
        rateEntity.setRateDate(java.time.LocalDate.parse(date));
        return rateEntity;
    }
}
