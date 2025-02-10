package com.example.unwise.domain;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transfer {
  private long id;
  private String sender;
  private String recipient;
  private double amount;
  private String reference;
  private Instant createdAt;
}
