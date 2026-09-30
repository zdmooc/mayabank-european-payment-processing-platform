package com.mayabank.processing.dispute;
import com.mayabank.processing.domain.*; import com.mayabank.processing.ledger.LedgerService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class DisputeServiceTest {
  private static SettlementResult settled(){
    return new SettlementResult(UUID.randomUUID(),UUID.randomUUID(),UUID.randomUUID(),SettlementState.SETTLED,new BigDecimal("100.00"),"EUR","SETTLED");
  }
  @Test void refundIsIdempotentAndPostsOnce(){
    var ledger=new LedgerService(); var service=new DisputeService(ledger); var s=settled();
    var a=service.refund(s,new BigDecimal("40.00"),"refund-1");
    var b=service.refund(s,new BigDecimal("40.00"),"refund-2");
    assertEquals(a.refundId(),b.refundId()); assertEquals(1,ledger.count());
  }
  @Test void refundIsDifferentFromReversal(){
    var result=new DisputeService(new LedgerService()).refund(settled(),new BigDecimal("25.00"),"r");
    assertEquals(RefundState.REFUNDED,result.state());
  }
  @Test void disputeLifecycleIsExplicit(){
    var service=new DisputeService(new LedgerService()); var c=service.openDispute(UUID.randomUUID(),"GOODS_NOT_RECEIVED");
    c=service.review(c.disputeId()); assertEquals(DisputeState.UNDER_REVIEW,c.state());
    c=service.resolve(c.disputeId(),true); assertEquals(DisputeState.CARDHOLDER_WON,c.state());
    c=service.close(c.disputeId()); assertEquals(DisputeState.CLOSED,c.state());
  }
}