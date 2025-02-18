package com.example.unwise.controller;

import com.example.unwise.domain.User;
import com.example.unwise.dto.CreateTransfer;
import com.example.unwise.repository.UserRepository;
import com.example.unwise.service.TransferService;
import jakarta.validation.Valid;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

  private final UserRepository userRepository;
  private final TransferService transferService;


  @GetMapping("")
  public String showTransferForm(Model model, Principal principal) {
    User user = userRepository.findByUsername(principal.getName()).orElseThrow();
    model.addAttribute("balance", user.getBalance());
    CreateTransfer createTransfer = CreateTransfer.builder().amount(10).build();
    model.addAttribute("createTransfer", createTransfer);
    return "transfer";
  }

  @PostMapping("")
  public String makeTransfer(@Valid CreateTransfer createTransfer,
      BindingResult result, Model model, RedirectAttributes redirectAttributes, Principal principal) {
    if (result.hasErrors()) {
      User user = userRepository.findByUsername(principal.getName()).orElseThrow();
      model.addAttribute("balance", user.getBalance());
      return "transfer";
    }
    try {
      transferService.transfer(createTransfer, principal.getName());
    } catch (IllegalArgumentException e) {
      result.reject("transfer.failure", e.getMessage());
      User user = userRepository.findByUsername(principal.getName()).orElseThrow();
      model.addAttribute("balance", user.getBalance());
      return "transfer";
    }
    redirectAttributes.addFlashAttribute("message", "Transfer successful");
    return "redirect:/";
  }
}
