# I12 Evidence — Security / API

**Status:** IMPLEMENTED / JAVA CI PASS / API RUNTIME_PROVEN / IAM-GATEWAY LIVE PENDING  
**Keycloak runtime:** NOT YET PROVEN  
**Gravitee runtime:** NOT YET PROVEN

## Implemented

- executable Spring Boot API module;
- REST reference endpoints for authorization, capture, clearing, settlement, reconciliation and state query;
- OAuth2 resource-server secured profile;
- payments.read / payments.write scope model;
- Keycloak realm reference configuration;
- Gravitee integration boundary;
- OpenAPI contract.

## Executed evidence

- Java Domain CI: run `36686041016` — **SUCCESS**.
- Spring Boot API E2E: run `36686201540` — **SUCCESS**.
- OAuth2/Keycloak/Gravitee assets are configured but were not exercised live.

## Claim boundary

This proves code/configuration and CI compilation/tests only until Keycloak/Gravitee are actually deployed and exercised.
