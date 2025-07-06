package com.example.etudiantapi.exception;

public class CustomException extends RuntimeException {
    
    public enum ErrorType {
        VALIDATION_ERROR,
        BUSINESS_RULE_VIOLATION,
        RESOURCE_NOT_FOUND,
        EXTERNAL_SERVICE_ERROR,
        INTERNAL_ERROR
    }
    
    private final ErrorType errorType;
    private final String errorCode;
    
    public CustomException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
        this.errorCode = generateErrorCode(errorType);
    }
    
    public CustomException(String message, ErrorType errorType, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
        this.errorCode = generateErrorCode(errorType);
    }
    
    public CustomException(String message, ErrorType errorType, String errorCode) {
        super(message);
        this.errorType = errorType;
        this.errorCode = errorCode;
    }
    
    private String generateErrorCode(ErrorType errorType) {
        switch (errorType) {
            case VALIDATION_ERROR:
                return "VALIDATION_001";
            case BUSINESS_RULE_VIOLATION:
                return "BUSINESS_001";
            case RESOURCE_NOT_FOUND:
                return "RESOURCE_001";
            case EXTERNAL_SERVICE_ERROR:
                return "EXTERNAL_001";
            case INTERNAL_ERROR:
            default:
                return "INTERNAL_001";
        }
    }
    
    public ErrorType getErrorType() {
        return errorType;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
