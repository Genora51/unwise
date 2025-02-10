package com.example.unwise.repository;

import com.example.unwise.domain.Transfer;
import com.example.unwise.dto.CreateTransfer;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TransferRepository {

  private static final String SQL_CREATE_TRANSFER = """
      INSERT INTO transfers (sender, recipient, amount, reference)
      VALUES (:sender, :recipient, :amount, :reference)
      """;

  private static final String SQL_SELECT_BY_SENDER = """
      SELECT id, sender, recipient, amount, reference, created_at
      FROM transfers WHERE sender = '%s' ORDER BY created_at DESC
      """;

  private static final String SQL_SELECT_BY_RECIPIENT = """
      SELECT id, sender, recipient, amount, reference, created_at
      FROM transfers WHERE recipient = '%s' ORDER BY created_at DESC
      """;

  private final NamedParameterJdbcTemplate jdbc;

  public void create(CreateTransfer transfer, String sender) {
    jdbc.update(SQL_CREATE_TRANSFER, Map.of(
        "sender", sender,
        "recipient", transfer.getRecipient(),
        "amount", transfer.getAmount(),
        "reference", transfer.getReference()
    ));
  }

  public List<Transfer> findAllBySenderUsername(String sender) {
    return jdbc.query(
        SQL_SELECT_BY_SENDER.formatted(sender),
        (rs, rowNum) -> Transfer.builder()
            .id(rs.getLong("id"))
            .sender(rs.getString("sender"))
            .recipient(rs.getString("recipient"))
            .amount(rs.getDouble("amount"))
            .reference(rs.getString("reference"))
            .createdAt(rs.getTimestamp("created_at").toInstant())
            .build()
    );
  }

  public List<Transfer> findAllByRecipientUsername(String recipient) {
    return jdbc.query(
        SQL_SELECT_BY_RECIPIENT.formatted(recipient),
        (rs, rowNum) -> Transfer.builder()
            .id(rs.getLong("id"))
            .sender(rs.getString("sender"))
            .recipient(rs.getString("recipient"))
            .amount(rs.getDouble("amount"))
            .reference(rs.getString("reference"))
            .createdAt(rs.getTimestamp("created_at").toInstant())
            .build()
    );
  }
}
