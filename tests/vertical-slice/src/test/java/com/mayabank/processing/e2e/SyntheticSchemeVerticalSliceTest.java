package com.mayabank.processing.e2e;
import com.mayabank.processing.acquiring.AcquirerProcessor;
import com.mayabank.processing.authorization.AuthorizationEngine;
import com.mayabank.processing.domain.*;
import com.mayabank.processing.issuer.IssuerProcessor;
import com.mayabank.processing.router.SchemeRouter;
import com.mayabank.processing.simulator.cb.CbSimulator;
import com.mayabank.processing.simulator.visa.VisaSimulator;
import com.mayabank.processing.simulator.mastercard.MastercardSimulator;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal; import java.util.List; import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class SyntheticSchemeVerticalSliceTest {
  private static CardPayment payment(Scheme scheme,String order){
    return new CardPayment(UUID.randomUUID(),order,new Merchant("M-1","A-1"),
      new Cardholder("C-1",new SyntheticCardToken("tok_demo_001","ISSUER-1")),
      scheme,new BigDecimal("125.50"),"EUR","corr-"+order);
  }

  private record Fixture(AuthorizationEngine engine,IssuerProcessor issuer,AcquirerProcessor acquirer){}
  private static Fixture fixture(){
    var engine=new AuthorizationEngine(); var issuer=new IssuerProcessor(engine);
    var router=new SchemeRouter(List.of(new CbSimulator(issuer),new VisaSimulator(issuer),new MastercardSimulator(issuer)));
    return new Fixture(engine,issuer,new AcquirerProcessor(router));
  }

  @Test void allSyntheticSchemesRoute(){
    for(var scheme:Scheme.values()){
      var f=fixture();
      var result=f.acquirer().authorize(payment(scheme,"ORDER-"+scheme),"idem-"+scheme,SchemeScenario.SUCCESS);
      assertEquals(AuthorizationState.AUTH_APPROVED,result.state());
      assertNotNull(result.schemeReference());
    }
  }

  @Test void timeoutAfterEffectIsUnknownOutsideButApprovedAtIssuer(){
    var f=fixture();
    var result=f.acquirer().authorize(payment(Scheme.VISA,"ORDER-TIMEOUT"),"idem-timeout",SchemeScenario.TIMEOUT_AFTER_EFFECT);
    assertEquals(AuthorizationState.AUTH_UNKNOWN,result.state());
    var issuerState=f.issuer().inquire(result.authorizationId().toString());
    assertNotNull(issuerState);
    assertEquals(AuthorizationState.AUTH_APPROVED,issuerState.state());
  }

  @Test void timeoutBeforeEffectCreatesNoIssuerEffect(){
    var f=fixture();
    var result=f.acquirer().authorize(payment(Scheme.CB,"ORDER-BEFORE"),"idem-before",SchemeScenario.TIMEOUT_BEFORE_EFFECT);
    assertEquals(AuthorizationState.AUTH_UNKNOWN,result.state());
    assertNull(f.issuer().inquire(result.authorizationId().toString()));
  }

  @Test void unavailableIsUnknownWithoutIssuerEffect(){
    var f=fixture();
    var result=f.acquirer().authorize(payment(Scheme.MASTERCARD,"ORDER-DOWN"),"idem-down",SchemeScenario.UNAVAILABLE);
    assertEquals("SCHEME_UNAVAILABLE",result.reason());
    assertNull(f.issuer().inquire(result.authorizationId().toString()));
  }
}