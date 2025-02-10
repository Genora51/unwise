package com.example.unwise.service;

import com.example.unwise.domain.Transfer;
import com.example.unwise.domain.TransferHistory;
import com.example.unwise.domain.User;
import com.example.unwise.dto.CreateTransfer;
import com.example.unwise.repository.TransferRepository;
import com.example.unwise.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {

  private final TransferRepository transferRepository;
  private final UserRepository userRepository;

  @Transactional
  public void transfer(CreateTransfer transfer, String senderUsername) {
    if (senderUsername.equals(transfer.getRecipient())) {
      throw new IllegalArgumentException("Cannot transfer to yourself");
    }
    User sender = userRepository.findByUsername(senderUsername)
        .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
    if (sender.getBalance() < transfer.getAmount()) {
      throw new IllegalArgumentException("Insufficient balance");
    }
    User recipient = userRepository.findByUsername(transfer.getRecipient())
        .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));
    userRepository.setBalance(sender.getUsername(), sender.getBalance() - transfer.getAmount());
    userRepository.setBalance(recipient.getUsername(), recipient.getBalance() + transfer.getAmount());
    transferRepository.create(transfer, sender.getUsername());
  }

  public TransferHistory getTransferHistory(String username) {
    List<Transfer> outgoingTransfers = transferRepository.findAllBySenderUsername(username);
    List<Transfer> incomingTransfers = transferRepository.findAllByRecipientUsername(username);
    return TransferHistory.builder()
        .outgoingTransfers(outgoingTransfers)
        .incomingTransfers(incomingTransfers)
        .build();
  }
}
