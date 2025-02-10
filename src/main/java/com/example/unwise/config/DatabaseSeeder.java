package com.example.unwise.config;

import com.example.unwise.domain.User;
import com.example.unwise.dto.CreateTransfer;
import com.example.unwise.repository.TransferRepository;
import com.example.unwise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

  private final UserRepository userRepository;
  private final TransferRepository transferRepository;
  private final FlagConfig flagConfig;
  private final PasswordEncoder passwordEncoder;

  private User createUserIfNotExists(String username, String password) {
    final var existingUser = userRepository.findByUsername(username);
    if (existingUser.isPresent()) {
      return existingUser.get();
    }
    final var user = User.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .build();
    userRepository.create(user);
    return user;
  }

  private void seed() {
    final var kristo = createUserIfNotExists("Kristo", flagConfig.getFlagPassword());
    userRepository.setBalance(kristo.getUsername(), 1_000_000);
    final var notKristo = createUserIfNotExists("DefinitelyNotKristo",
        flagConfig.getFlagPassword());
    final var allTransfers = transferRepository.findAllBySenderUsername(kristo.getUsername());
    if (allTransfers.stream()
        .anyMatch(t -> t.getReference().equals(flagConfig.getFlagTransaction()))) {
      return;
    }
    final var transfer = CreateTransfer.builder()
        .recipient(notKristo.getUsername())
        .amount(12_345)
        .reference(flagConfig.getFlagTransaction())
        .build();
    transferRepository.create(transfer, kristo.getUsername());
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onApplicationReady() {
    seed();
  }
}
