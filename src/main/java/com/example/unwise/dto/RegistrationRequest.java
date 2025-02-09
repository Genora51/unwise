package com.example.unwise.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {
  @NotNull
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;
  @NotNull
  @NotBlank
  @Size(min = 6, max = 20)
  private String password;
  @NotNull
  @NotBlank
  private String confirmPassword;
}
