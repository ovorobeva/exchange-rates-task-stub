package com.shipmonk.testingday;

import com.shipmonk.testingday.controller.ExchangeRatesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestingdayExchangeRatesApplicationTest {

    @Autowired
    private ExchangeRatesController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }
}
