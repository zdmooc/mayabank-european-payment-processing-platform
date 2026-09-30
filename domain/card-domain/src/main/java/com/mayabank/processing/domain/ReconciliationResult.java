package com.mayabank.processing.domain;
import java.util.UUID;
public record ReconciliationResult(UUID caseId,String plane,String beforeState,String afterState,String action,boolean resolved) {}