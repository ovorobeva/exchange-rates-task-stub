package com.shipmonk.testingday.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entity class representing a currency rate.
 * The design may not be optimal from the perspective of having a record for each currency and date,
 * however it was chosen for scalability and maintainability reasons in case in the future we might need
 * functionality of historical rates monitoring
 * or new currencies are added or necessity of querying specific currency rate.
 * Base rate is always considered to be EUR as defined by rate provider.
 *
 * I'd normally use CurrencyUnit from Joda Money library for currency representation,
 * but it doesn't support cryptocurrencies like BTC, so String is used instead.
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "rates")
public class RateEntity {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private UUID rateId;
    private LocalDate rateDate;

    @Pattern(regexp = "[A-Z][A-Z][A-Z]")
    private String currency;
    private Double rateValue;
}
