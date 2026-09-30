package com.mayabank.processing.ledger;
import java.math.BigDecimal; import java.util.List; import java.util.UUID;
public record LedgerMovement(UUID movementId,String type,String reference,List<LedgerLine> lines) {
  public BigDecimal net(){ return lines.stream().map(l->"DEBIT".equals(l.direction())?l.amount():l.amount().negate()).reduce(BigDecimal.ZERO,BigDecimal::add); }
}