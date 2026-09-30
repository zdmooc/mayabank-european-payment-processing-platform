package com.mayabank.processing.capture;
import com.mayabank.processing.domain.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CaptureServiceTest {
  private static CardPayment payment(){
    return new CardPayment(UUID.randomUUID(),"ORDER-CAP",new Merchant("M","A"),
      new Cardholder("C",new SyntheticCardToken("tok_capture","ISS")),Scheme.VISA,new BigDecimal("100.00"),"EUR","corr-cap");
  }
  private static AuthorizationResult approved(){
    return new AuthorizationResult(UUID.randomUUID(),AuthorizationState.AUTH_APPROVED,"SCHEME-1","ISS-1","APPROVED");
  }

  @Test void oneAuthorizationProducesAtMostOneCaptureEffect(){
    var service=new CaptureService(); var payment=payment(); var auth=approved();
    var first=service.capture(payment,auth,new BigDecimal("75.00"),"cap-key-1");
    var retryDifferentKey=service.capture(payment,auth,new BigDecimal("75.00"),"cap-key-2");
    assertEquals(first.captureId(),retryDifferentKey.captureId());
  }

  @Test void declinedAuthorizationCannotBeCaptured(){
    var service=new CaptureService(); var payment=payment();
    var declined=new AuthorizationResult(UUID.randomUUID(),AuthorizationState.AUTH_DECLINED,null,"ISS-1","DECLINED");
    assertThrows(IllegalStateException.class,()->service.capture(payment,declined,new BigDecimal("10.00"),"cap-key"));
  }

  @Test void reversalIsIdempotent(){
    var service=new CaptureService(); var p=payment(); var captured=service.capture(p,approved(),new BigDecimal("50.00"),"cap-key");
    var r1=service.reverse(captured.captureId()); var r2=service.reverse(captured.captureId());
    assertEquals(CaptureState.REVERSED,r1.state()); assertEquals(r1,r2);
  }
}