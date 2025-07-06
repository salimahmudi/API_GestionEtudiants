package com.example.etudiantapi.exception;

import com.example.etudiantapi.util.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @Autowired
    private LanguageUtil languageUtil;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, Object>> handleCustomException(
            CustomException ex, WebRequest request) {

        String lang = extractLanguageFromRequest(request);
        HttpStatus status = getHttpStatusFromErrorType(ex.getErrorType());

        LOGGER.log(Level.WARNING, "Custom exception occurred: " + ex.getMessage(), ex);

        Map<String, Object> errorResponse = createErrorResponse(
            status.value(),
            ex.getMessage(),
            ex.getErrorCode(),
            request.getDescription(false),
            lang
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {

        String lang = extractLanguageFromRequest(request);

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        LOGGER.warning("Validation error: " + fieldErrors);

        Map<String, Object> errorResponse = createErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            languageUtil.getMessage("error.validation.failed", lang),
            "VALIDATION_001",
            request.getDescription(false),
            lang
        );
        errorResponse.put("fieldErrors", fieldErrors);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
            ConstraintViolationException ex, WebRequest request) {

        String lang = extractLanguageFromRequest(request);

        Map<String, String> violations = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            violations.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        LOGGER.warning("Constraint violation: " + violations);

        Map<String, Object> errorResponse = createErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            languageUtil.getMessage("error.validation.failed", lang),
            "VALIDATION_002",
            request.getDescription(false),
            lang
        );
        errorResponse.put("violations", violations);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        String lang = extractLanguageFromRequest(request);

        LOGGER.warning("Illegal argument: " + ex.getMessage());

        Map<String, Object> errorResponse = createErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            languageUtil.getMessage("error.bad.request", lang),
            "BAD_REQUEST_001",
            request.getDescription(false),
            lang
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(
            Exception ex, WebRequest request) {

        String lang = extractLanguageFromRequest(request);

        LOGGER.log(Level.SEVERE, "Unexpected error occurred: " + ex.getMessage(), ex);

        Map<String, Object> errorResponse = createErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            languageUtil.getMessage("error.internal.server", lang),
            "INTERNAL_001",
            request.getDescription(false),
            lang
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private String extractLanguageFromRequest(WebRequest request) {
        String lang = request.getParameter("lang");
        return (lang != null && !lang.isEmpty()) ? lang : "fr";
    }

    private HttpStatus getHttpStatusFromErrorType(CustomException.ErrorType errorType) {
        switch (errorType) {
            case VALIDATION_ERROR:
                return HttpStatus.BAD_REQUEST;
            case BUSINESS_RULE_VIOLATION:
                return HttpStatus.CONFLICT;
            case RESOURCE_NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case EXTERNAL_SERVICE_ERROR:
                return HttpStatus.SERVICE_UNAVAILABLE;
            case INTERNAL_ERROR:
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private Map<String, Object> createErrorResponse(int status, String message, String errorCode,
                                                   String path, String lang) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("status", status);
        errorResponse.put("error", getErrorTypeFromStatus(status));
        errorResponse.put("message", message);
        errorResponse.put("errorCode", errorCode);
        errorResponse.put("path", path.replace("uri=", ""));
        errorResponse.put("language", lang);
        errorResponse.put("version", detectApiVersion(path));

        return errorResponse;
    }

    private String getErrorTypeFromStatus(int status) {
        switch (status) {
            case 400: return "Bad Request";
            case 404: return "Not Found";
            case 409: return "Conflict";
            case 500: return "Internal Server Error";
            case 503: return "Service Unavailable";
            default: return "Error";
        }
    }

    private String detectApiVersion(String path) {
        if (path.contains("/api/v2/")) {
            return "2.0";
        } else if (path.contains("/api/v1/")) {
            return "1.0";
        }
        return "unknown";
    }
}
