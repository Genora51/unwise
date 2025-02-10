package com.example.unwise.controller;

import com.example.unwise.domain.TransferHistory;
import com.example.unwise.service.TransferService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/history")
public class TransferHistoryController {
  private final TransferService transferService;

  @GetMapping("")
  public String getHistory(Principal principal, Model model) {
    final TransferHistory history = transferService.getTransferHistory(principal.getName());
    model.addAttribute("outgoing", history.getOutgoingTransfers());
    model.addAttribute("incoming", history.getIncomingTransfers());
    return "history";
  }
}
