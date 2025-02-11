package com.example.unwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
  @Size(max = 255)
  private String reference;
}
