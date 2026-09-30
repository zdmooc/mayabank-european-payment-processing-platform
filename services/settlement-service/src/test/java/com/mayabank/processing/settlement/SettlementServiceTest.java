package com.mayabank.processing.settlement;
import com.mayabank.processing.domain.*; import com.mayabank.processing.ledger.LedgerService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class SettlementServiceTest {
  private static ClearingPosition cleared(){
    return new ClearingPosition(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),ClearingState.CLEARED,new BigDecimal("88.00"),"EUR","CLEARED");
  }
  @Test void settlementPostsLedgerOnce(){
    var ledger=new LedgerService(); var s=new SettlementService(ledger); var c=cleared();
    var a=s.settle(c,SettlementService.Scenario.SUCCESS); var b=s.settle(c,SettlementService.Scenario.SUCCESS);
    assertEquals(SettlementState.SETTLED,a.state()); assertEquals(a.settlementId(),b.settlementId()); assertEquals(1,ledger.count());
  }
  @Test void unknownSettlementDoesNotPostLedger(){
    var ledger=new LedgerService(); var r=new SettlementService(ledger).settle(cleared(),SettlementService.Scenario.UNKNOWN);
    assertEquals(SettlementState.SETTLEMENT_UNKNOWN,r.state()); assertEquals(0,ledger.count());
  }
}