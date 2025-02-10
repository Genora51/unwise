package com.example.unwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTransfer {
  @NotNull
  private String recipient;
  @NotNull
  private double amount;
  @NotBlank(message = "Reference is required")
  private String reference;
}
