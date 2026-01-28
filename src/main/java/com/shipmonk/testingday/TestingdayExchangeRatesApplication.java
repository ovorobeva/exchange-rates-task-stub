package com.shipmonk.testingday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories
@EnableConfigurationProperties
public class TestingdayExchangeRatesApplication
{
    static void main(String[] args)
    {
        SpringApplication.run(TestingdayExchangeRatesApplication.class, args);
    }

}
