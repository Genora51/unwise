package com.example.unwise.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "flags")
public class FlagConfig {
  private String flagBillionaire;
  private String flagTransaction;
  private String flagPassword;
}
