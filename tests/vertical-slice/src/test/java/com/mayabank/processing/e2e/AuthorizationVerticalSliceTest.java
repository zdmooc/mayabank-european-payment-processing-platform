package com.mayabank.processing.e2e;
import com.mayabank.processing.acquiring.AcquirerProcessor;
import com.mayabank.processing.authorization.AuthorizationEngine;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.issuer.IssuerProcessor;
import com.mayabank.processing.router.SchemeRouter;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; import java.util.List; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class AuthorizationVerticalSliceTest {
  private static CardPayment payment(Scheme scheme){
    return new CardPayment(UUID.randomUUID(),"ORDER-1",new Merchant("M-1","A-1"),
      new Cardholder("C-1",new SyntheticCardToken("tok_demo_001","ISSUER-1")),
      scheme,new BigDecimal("125.50"),"EUR","corr-1");
  }

  @Test void merchantToIssuerApprovesAndDeduplicates(){
    var engine=new AuthorizationEngine(); var issuer=new IssuerProcessor(engine);
    SchemeAuthorizationPort direct=new SchemeAuthorizationPort(){
      public Scheme scheme(){ return Scheme.VISA; }
      public AuthorizationResult authorize(AuthorizationRequest r,SchemeScenario s){ return issuer.authorize(r,s); }
    };
    var acquirer=new AcquirerProcessor(new SchemeRouter(List.of(direct)));
    var p=payment(Scheme.VISA);
    var first=acquirer.authorize(p,"idem-1",SchemeScenario.SUCCESS);
    var replay=acquirer.authorize(p,"idem-1",SchemeScenario.SUCCESS);
    assertEquals(AuthorizationState.AUTH_APPROVED,first.state());
    assertEquals(first.authorizationId(),replay.authorizationId());
  }

  @Test void issuerDeclineIsExplicit(){
    var engine=new AuthorizationEngine(); var issuer=new IssuerProcessor(engine);
    SchemeAuthorizationPort direct=new SchemeAuthorizationPort(){
      public Scheme scheme(){ return Scheme.CB; }
      public AuthorizationResult authorize(AuthorizationRequest r,SchemeScenario s){ return issuer.authorize(r,s); }
    };
    var result=new AcquirerProcessor(new SchemeRouter(List.of(direct))).authorize(payment(Scheme.CB),"idem-2",SchemeScenario.DECLINE);
    assertEquals(AuthorizationState.AUTH_DECLINED,result.state());
  }
}