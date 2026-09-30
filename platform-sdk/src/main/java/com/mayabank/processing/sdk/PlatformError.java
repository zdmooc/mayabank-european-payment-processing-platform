package com.mayabank.processing.sdk;
public record PlatformError(String code,String message,boolean retryable) {}