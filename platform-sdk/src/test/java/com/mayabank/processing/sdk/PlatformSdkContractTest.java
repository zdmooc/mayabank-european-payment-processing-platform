package com.mayabank.processing.sdk;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PlatformSdkContractTest {
  @Test void correlationIsGeneratedWhenMissing(){ assertFalse(CorrelationContext.create().correlationId().isBlank()); }
  @Test void blankIdempotencyKeyIsRejected(){ assertThrows(IllegalArgumentException.class,()->new IdempotencyKey(" ")); }
  @Test void eventEnvelopeCarriesCorrelation(){ var e=EventEnvelope.of("AUTH_RECEIVED","corr-1","payload"); assertEquals("corr-1",e.correlationId()); assertEquals(1,e.eventVersion()); }
}