package com.shipmonk.testingday.service;

import com.shipmonk.testingday.model.dtos.RateDto;
import org.joda.money.CurrencyUnit;

import java.time.LocalDate;

public interface RateService {
    RateDto getRates(LocalDate day, CurrencyUnit currency);
}
