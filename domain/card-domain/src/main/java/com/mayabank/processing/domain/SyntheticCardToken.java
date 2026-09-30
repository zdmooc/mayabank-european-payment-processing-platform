package com.mayabank.processing.domain;
public record SyntheticCardToken(String token,String issuerId) {
  public SyntheticCardToken {
    if (token == null || !token.startsWith("tok_")) throw new IllegalArgumentException("Only synthetic tokenized cards are accepted");
    if (issuerId == null || issuerId.isBlank()) throw new IllegalArgumentException("issuerId is required");
  }
}