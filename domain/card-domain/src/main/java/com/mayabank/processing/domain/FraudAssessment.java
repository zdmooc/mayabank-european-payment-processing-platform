package com.mayabank.processing.domain;
import java.util.List;
public record FraudAssessment(FraudDecision decision,int riskScore,List<String> reasons) {}