package com.mayabank.processing.domain; import java.util.UUID;
public record AuthorizationRequest(UUID authorizationId,CardPayment payment,String idempotencyKey) {}