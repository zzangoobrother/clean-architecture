package com.example.cleanarchitecture.config;

import com.example.cleanarchitecture.application.service.MoneyTransferProperties;
import com.example.cleanarchitecture.domain.Money;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CleanArchitectureConfigurationProperties.class)
public class CleanArchitectureConfiguration {

  @Bean
  public MoneyTransferProperties moneyTransferProperties(CleanArchitectureConfigurationProperties cleanArchitectureConfigurationProperties) {
    return new MoneyTransferProperties(Money.of(cleanArchitectureConfigurationProperties.getTransferThreshold()));
  }
}
