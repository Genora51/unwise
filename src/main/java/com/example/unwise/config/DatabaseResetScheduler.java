package com.example.unwise.config;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseResetScheduler {
  private static final String DELETE_TRANSFERS = """
      DELETE FROM transfers
      WHERE reference != :flag""";

  private static final String DELETE_USERS = """
      DELETE FROM users
      WHERE username NOT IN (:usernames)""";

  private final NamedParameterJdbcTemplate jdbc;
  private final FlagConfig flagConfig;

  @Scheduled(cron = "0 0 0 * * *")
  public void resetDatabase() {
    log.info("Resetting database");
    jdbc.update(DELETE_TRANSFERS, Map.of("flag", flagConfig.getFlagTransaction()));
    jdbc.update(DELETE_USERS, Map.of(
        "usernames", List.of("Kristo", "DefinitelyNotKristo")
    ));
    log.info("Database reset complete");
  }
}
