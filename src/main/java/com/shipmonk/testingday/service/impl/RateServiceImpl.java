package com.shipmonk.testingday.service.impl;

import com.shipmonk.testingday.FixerProperty;
import com.shipmonk.testingday.client.FixerClient;
import com.shipmonk.testingday.model.dtos.RateDto;
import com.shipmonk.testingday.service.RateService;
import lombok.RequiredArgsConstructor;
import org.joda.money.CurrencyUnit;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    final FixerClient fixerClient;
    final FixerProperty fixerProperty;

    @Override
    public RateDto getRates(LocalDate day, CurrencyUnit currency) {
        final ResponseEntity<RateDto> ratesResponse = fixerClient.getRates(fixerProperty.getAccessKey(), day);
        if (ratesResponse.getStatusCode().is2xxSuccessful() && ratesResponse.getBody() != null) {
            final RateDto rateDto = ratesResponse.getBody();
            if (!currency.equals(CurrencyUnit.USD)) {
                double baseRate = rateDto.getRates().get(currency.getCode());
                rateDto.getRates().replaceAll((cur, rate) -> rate * baseRate);
            }
            return rateDto;
        } else {
            throw new RuntimeException("Failed to fetch rates from Fixer API");
        }
    }
}
