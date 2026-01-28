package com.shipmonk.testingday.service.impl;

import com.shipmonk.testingday.configuration.FixerProperty;
import com.shipmonk.testingday.client.FixerClient;
import com.shipmonk.testingday.mappers.RateMapper;
import com.shipmonk.testingday.model.dtos.RateDto;
import com.shipmonk.testingday.model.entities.RateEntity;
import com.shipmonk.testingday.repository.RatesRepository;
import com.shipmonk.testingday.service.RateService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    final private FixerClient fixerClient;
    final private FixerProperty fixerProperty;
    final private RatesRepository ratesRepository;
    final private RateMapper rateMapper;

    @Override
    public ResponseEntity<RateDto> getRates(final LocalDate day, final @Pattern(regexp = "[a-zA-Z][a-zA-Z][a-zA-Z]") String currency) {
        log.info("Fetching rates for date: {} and currency: {}", day, currency);

        final RateDto rateDto;
        final Optional<List<RateEntity>> rates = ratesRepository.findAllByRateDate(day);

        if (rates.isPresent() && !rates.get().isEmpty()) {
            log.info("Found {} rates in the database for date: {}", rates.get().size(), day);
            rateDto = rateMapper.mapEntityToRateDto(rates.get());
            return ResponseEntity.ok(recalculateRate(currency, rateDto));
        } else {
            log.info("No rates found in the database for date: {}. Fetching from Fixer API.", day);
            final ResponseEntity<RateDto> ratesResponse = fixerClient.getRates(day, fixerProperty.getAccessKey());
            return getRatesFromFixer(currency, ratesResponse);
        }
    }

    private @NonNull ResponseEntity<RateDto> getRatesFromFixer(final String currency, ResponseEntity<RateDto> ratesResponse) {
        final RateDto rateDto;
        if (ratesResponse.getStatusCode().is2xxSuccessful() && ratesResponse.getBody() != null) {
            rateDto = ratesResponse.getBody();
            ratesRepository.saveAll(
                rateMapper.mapRateDtoToEntity(rateDto)
            );
            ratesRepository.flush();
            return ResponseEntity.ok(recalculateRate(currency, rateDto));
        } else {
            return ResponseEntity.status(ratesResponse.getStatusCode()).build();
        }
    }

    private static RateDto recalculateRate(final String currency, final RateDto rateDto) {
        log.info("Recalculating rates based on currency: {}", currency);
        if (!currency.equalsIgnoreCase("EUR")) {
            final double baseRate = rateDto.getRates().getOrDefault(currency.toUpperCase(), 1.0);
            log.info("Base rate for {} to 1 EUR: {}", currency, baseRate);
            if (baseRate == 1.0) {
                return rateDto;
            }
            rateDto.getRates().replaceAll((_, rate) -> rate / baseRate);
        } else {
            log.info("Currency is EUR, no recalculation needed.");
        }
        return rateDto;
    }
}
