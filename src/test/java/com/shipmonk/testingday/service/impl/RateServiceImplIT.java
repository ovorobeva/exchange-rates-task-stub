package com.shipmonk.testingday.service.impl;

import com.shipmonk.testingday.client.FixerClient;
import com.shipmonk.testingday.mappers.RateMapper;
import com.shipmonk.testingday.model.dtos.RateDto;
import com.shipmonk.testingday.model.entities.RateEntity;
import com.shipmonk.testingday.repository.RatesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDate;
import java.util.List;

import static com.shipmonk.testingday.TestDataMother.createRateDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RateServiceImplIT {

    @Autowired
    RateServiceImpl rateServiceImpl;

    @Autowired
    RatesRepository ratesRepository;

    @MockitoBean
    FixerClient fixerClient;

    @MockitoSpyBean
    RateMapper rateMapper;

    @BeforeEach
    void setUp() {
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void getRatesFromClient(LocalDate date, String currency) {
        when(fixerClient.getRates(any(LocalDate.class), anyString()))
            .thenReturn(ResponseEntity.ok(createRateDto(date)));

        assertEquals(0L, ratesRepository.count());
        final RateDto rateDto = rateServiceImpl.getRates(date, currency).getBody();

        assertEquals(date, LocalDate.parse(rateDto.getDate()));
        final List<RateEntity> rateEntities = ratesRepository.findAllByRateDate(date).get();
        assertEquals(rateDto.getRates().size(),
            rateEntities.size());

        verify(fixerClient).getRates(eq(date), anyString());
        verify(rateMapper, never()).mapEntityToRateDto(anyList());
        verify(rateMapper).mapRateDtoToEntity(any(RateDto.class));
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void getRatesFromDbAfterClient(LocalDate date, String currency) {
        when(fixerClient.getRates(any(LocalDate.class), anyString()))
            .thenReturn(ResponseEntity.ok(createRateDto(date)));

        final RateDto rateDto = rateServiceImpl.getRates(date, currency).getBody();
        final RateDto rateDtoFromDb = rateServiceImpl.getRates(date, currency).getBody();

        assertEquals(date, LocalDate.parse(rateDto.getDate()));
        assertEquals(rateDto, rateDtoFromDb);

        final List<RateEntity> rateEntities = ratesRepository.findAllByRateDate(date).get();
        assertEquals(rateDto.getRates().size(),
            rateEntities.size());

        verify(rateMapper, times(1)).mapRateDtoToEntity(any(RateDto.class));
        verify(rateMapper, times(1)).mapEntityToRateDto(anyList());
        verify(fixerClient, times(1)).getRates(eq(date), anyString());
    }

    @AfterEach
    void tearDown() {
        ratesRepository.deleteAll();
        ratesRepository.flush();
    }

    private static List<Arguments> getTestData() {
        return List.of(
            Arguments.of(LocalDate.of(2023, 1, 1), "USD"),
            Arguments.of(LocalDate.of(2025, 2, 1), "EUR"),
            Arguments.of(LocalDate.of(1970, 3, 1), "GBP")
        );
    }
}
