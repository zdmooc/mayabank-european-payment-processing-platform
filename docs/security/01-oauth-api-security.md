# OAuth2 / OIDC / API Security

**Iteration:** I12  
**Status:** IMPLEMENTED + CONFIGURED / RUNTIME IDP & GATEWAY PENDING

## Scopes

| Scope | Purpose |
|---|---|
| payments.read | read payment state |
| payments.write | authorization/capture/clearing/settlement/reconciliation operations |

The Spring Boot API has:
- local profile: permit-all for lab bootstrap;
- `secured` profile: JWT resource server;
- issuer URI configurable for Keycloak.

Keycloak and Gravitee assets are reference configurations until runtime evidence is produced.

No real PAN is accepted by the domain model; only synthetic `tok_*` values.
