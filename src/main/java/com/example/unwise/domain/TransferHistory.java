package com.example.unwise.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferHistory {
  private List<Transfer> outgoingTransfers;
  private List<Transfer> incomingTransfers;
}
