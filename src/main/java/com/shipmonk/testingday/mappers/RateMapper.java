package com.shipmonk.testingday.mappers;

import com.shipmonk.testingday.model.dtos.RateDto;
import com.shipmonk.testingday.model.entities.RateEntity;
import org.mapstruct.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RateMapper {

    default RateDto mapEntityToRateDto(final List<RateEntity> rateEntities) {
        if (rateEntities == null || rateEntities.isEmpty()) {
            return null;
        }

        final RateDto rateDto = new RateDto();
        rateDto.setDate(rateEntities.getFirst().getRateDate().toString());
        rateDto.setRates(mapRatesListToMap(rateEntities));
        return rateDto;
    }

    default Map<String, Double> mapRatesListToMap(List<RateEntity> rateEntities) {
        return rateEntities
            .stream()
            .collect(Collectors
                .toMap(rateEntity -> rateEntity.getCurrency().toUpperCase(),
                    RateEntity::getRateValue
        ));
    }

    default List<RateEntity> mapRateDtoToEntity(final RateDto rateDto) {
        return rateDto.getRates().entrySet().stream()
                .map(entry -> {
                    final RateEntity rateEntity = new RateEntity();
                    rateEntity.setCurrency(entry.getKey().toUpperCase());
                    rateEntity.setRateValue(entry.getValue());
                    rateEntity.setRateDate(LocalDate.parse(rateDto.getDate()));
                    return rateEntity;
                })
                .collect(Collectors.toList());
    }
}
