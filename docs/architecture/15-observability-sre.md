# Observability / SRE

**Iteration:** I13  
**Status:** IMPLEMENTED + CONFIGURED / FULL RUNTIME OBSERVABILITY PENDING

## Correlation

Every HTTP request receives or propagates `X-Correlation-Id`.

Target correlation chain:

```text
merchantOrderId
paymentId
authorizationId
schemeReference
issuerReference
captureId
clearingId
settlementId
correlationId
```

## Tooling

- Spring Boot Actuator;
- Prometheus endpoint;
- Prometheus rule examples;
- Grafana reference dashboard;
- OpenTelemetry Collector configuration;
- structured correlation via MDC.

## Business/SRE signals

- authorization rate;
- approval/decline/UNKNOWN rates;
- latency percentiles;
- Kafka lag when Kafka runtime is enabled;
- open reconciliation cases;
- clearing exceptions;
- settlement failures;
- duplicate-prevention counters.

All dashboards/rules remain reference configurations until executed in a runtime environment.
