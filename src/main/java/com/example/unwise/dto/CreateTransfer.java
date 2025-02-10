package com.example.unwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTransfer {
  @NotNull
  private String recipient;
  @NotNull
  private double amount;
  @NotBlank(message = "Reference is required")
  private String reference;
}
