package com.mayabank.processing.clearing;
import com.mayabank.processing.domain.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
class ClearingServiceTest {
  private static CardPayment payment(){
    return new CardPayment(UUID.randomUUID(),"ORDER-CLR",new Merchant("M","A"),new Cardholder("C",new SyntheticCardToken("tok_clear","ISS")),Scheme.CB,new BigDecimal("25.00"),"EUR","corr");
  }
  private static CaptureResult capture(){ return new CaptureResult(UUID.randomUUID(),UUID.randomUUID(),CaptureState.CAPTURED,new BigDecimal("25.00"),"CAPTURED"); }

  @Test void captureBecomesSingleClearingPosition(){
    var s=new ClearingService(); var p=payment(); var c=capture();
    var a=s.clear(p,c,ClearingService.Scenario.SUCCESS); var b=s.clear(p,c,ClearingService.Scenario.SUCCESS);
    assertEquals(ClearingState.CLEARED,a.state()); assertEquals(a.clearingId(),b.clearingId()); assertEquals(1,s.count());
  }
  @Test void clearingExceptionIsExplicit(){
    var r=new ClearingService().clear(payment(),capture(),ClearingService.Scenario.EXCEPTION);
    assertEquals(ClearingState.CLEARING_EXCEPTION,r.state());
  }
}