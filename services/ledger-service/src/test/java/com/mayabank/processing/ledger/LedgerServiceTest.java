package com.mayabank.processing.ledger;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class LedgerServiceTest {
  @Test void movementIsBalanced(){
    var s=new LedgerService(); var m=s.postCapture(UUID.randomUUID(),UUID.randomUUID(),new BigDecimal("125.50"),"EUR");
    assertEquals(0,m.net().signum()); assertEquals(2,m.lines().size());
  }
  @Test void duplicateFinancialReferenceDoesNotDoublePost(){
    var s=new LedgerService(); var p=UUID.randomUUID(); var capture=UUID.randomUUID();
    var a=s.postCapture(p,capture,new BigDecimal("42.00"),"EUR");
    var b=s.postCapture(p,capture,new BigDecimal("42.00"),"EUR");
    assertEquals(a.movementId(),b.movementId()); assertEquals(1,s.count());
  }
}