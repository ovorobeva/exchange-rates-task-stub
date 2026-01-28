package com.shipmonk.testingday.repository;

import com.shipmonk.testingday.model.entities.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatesRepository extends JpaRepository<RateEntity, UUID> {

    Optional<List<RateEntity>> findAllByRateDate(LocalDate date);
}
