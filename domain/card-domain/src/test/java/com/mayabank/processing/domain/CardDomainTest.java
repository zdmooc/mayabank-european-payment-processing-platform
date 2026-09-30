package com.mayabank.processing.domain;
import org.junit.jupiter.api.Test; import java.math.BigDecimal; import java.util.UUID; import static org.junit.jupiter.api.Assertions.*;
class CardDomainTest {
  @Test void rawPanLikeValueIsRejected(){ assertThrows(IllegalArgumentException.class,()->new SyntheticCardToken("4111111111111111","issuer-1")); }
  @Test void captureLifecycleIsSeparated(){ var c=new Capture(UUID.randomUUID(),UUID.randomUUID(),new BigDecimal("42.00")); assertEquals(CaptureState.CAPTURE_PENDING,c.state()); c.markCaptured(); c.reverse(); assertEquals(CaptureState.REVERSED,c.state()); }
}