package com.example.unwise.controller;

import com.example.unwise.domain.User;
import com.example.unwise.dto.RegistrationRequest;
import com.example.unwise.repository.UserRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("/login")
  public String login(Model model, @RequestParam(required = false) String error, @ModelAttribute("message") String message) {
    if (error != null) {
      model.addAttribute("loginError", true);
    }
    if (message != null) {
      model.addAttribute("message", message);
    }
    return "auth/login";
  }

  @GetMapping("/register")
  public String register() {
    return "auth/register";
  }

  @PostMapping("/register")
  public String submitRegistration(
      @Valid RegistrationRequest registrationRequest, BindingResult result, Model model,
      RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      model.addAttribute("error", "Validation failed");
      return "auth/register";
    }

    if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
      model.addAttribute("error", "Passwords do not match");
      return "auth/register";
    }

    Optional<User> existingUser = userRepository.findByUsername(registrationRequest.getUsername());
    if (existingUser.isPresent()) {
      model.addAttribute("error", "User already exists");
      return "auth/register";
    }

    String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
    log.info(encodedPassword);

    User user = User.builder()
        .username(registrationRequest.getUsername())
        .password(encodedPassword)
        .build();
    userRepository.create(user);
    redirectAttributes.addFlashAttribute("message", "User created successfully - please log in");
    return "redirect:/login";
  }
}
