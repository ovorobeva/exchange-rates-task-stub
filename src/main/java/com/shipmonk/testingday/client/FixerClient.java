package com.shipmonk.testingday.client;

import com.shipmonk.testingday.model.dtos.RateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "fixerClient", url = "${fixer.api-url}")
public interface FixerClient {

    @GetMapping("/latest")
    ResponseEntity<RateDto> getRates(@RequestParam String access_key, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam LocalDate date);
}
