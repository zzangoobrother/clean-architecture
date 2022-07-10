package com.example.cleanarchitecture.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cleanarchitecture")
public class CleanArchitectureConfigurationProperties {

  private long transferThreshold = Long.MAX_VALUE;
}
