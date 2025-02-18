package com.example.unwise.controller;

import com.example.unwise.config.FlagConfig;
import com.example.unwise.domain.User;
import com.example.unwise.repository.UserRepository;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {
  private final UserRepository userRepository;
  private final FlagConfig flagConfig;

  @GetMapping("/")
  public String dashboard(Principal principal, Model model) {
    User user = userRepository.findByUsername(principal.getName()).orElseThrow();
    model.addAttribute("username", user.getUsername());
    model.addAttribute("balance", user.getBalance());
    if (user.getBalance() > 1_000_000_000) {
      model.addAttribute("flag", flagConfig.getFlagBillionaire());
    }
    return "dashboard";
  }
}
