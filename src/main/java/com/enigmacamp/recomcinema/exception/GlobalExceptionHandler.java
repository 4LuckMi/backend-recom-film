package com.enigmacamp.recomcinema.exception;

import com.enigmacamp.recomcinema.model.response.ErrorResponse;
import com.enigmacamp.recomcinema.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseUtil.buildErrorResponse(
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                List.of(ex.getMessage())
        );
    }

    // 400 - Type mismatch (path variable, param, dll)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        Object invalidValue = ex.getValue();
        String requiredType = ex.getRequiredType() != null
                ? ex.getRequiredType().getSimpleName()
                : "unknown";

        String error;
        if ("Long".equals(requiredType) || "Integer".equals(requiredType)) {
            error = String.format("Parameter '%s' must be a valid number. Invalid value: '%s'",
                    paramName, invalidValue);
        } else {
            error = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s",
                    invalidValue, paramName, requiredType);
        }

        return ResponseUtil.buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                List.of(error)
        );
    }

    // 400 - Validation error (e.g. @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + ": " + errorMessage);
        });

        return ResponseUtil.buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                errors
        );
    }

    // ResponseStatusException (400, 401, 403, dll)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        String message = ex.getReason() != null ? ex.getReason() : status.getReasonPhrase();
        return ResponseUtil.buildErrorResponse(
                status,
                message,
                List.of(message)
        );
    }

    // 401 - Bad Credentials
    @ExceptionHandler({})
    public ResponseEntity<ErrorResponse> handleBadCredentials(Exception ex) {
        return ResponseUtil.buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Invalid username or password",
                List.of(ex.getMessage())
        );
    }

    // 403 - Locked or Disabled Account
//    @ExceptionHandler({LockedException.class, DisabledException.class})
//    public ResponseEntity<ErrorResponse> handleAccountLocked(Exception ex) {
//        return ResponseUtil.buildErrorResponse(
//                HttpStatus.FORBIDDEN,
//                "Account is locked or disabled",
//                List.of(ex.getMessage())
//        );
//    }

    // 401 - JWT Error
//    @ExceptionHandler(JwtAuthenticationException.class)
//    public ResponseEntity<ErrorResponse> handleJwtError(JwtAuthenticationException ex) {
//        return ResponseUtil.buildErrorResponse(
//                HttpStatus.UNAUTHORIZED,
//                "Invalid or expired token",
//                List.of(ex.getMessage())
//        );
//    }

    // 500 - Server Error (umum)
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
//        // 403 -> AccessDenied
//        if(ex instanceof AccessDeniedException){
//            throw (AccessDeniedException) ex;
//        }
//        if(ex instanceof AuthenticationException){
//            throw (AuthenticationException) ex;
//        }
//
//        List<String> errors = List.of("Internal Server Error: " + ex.getMessage());
//        return ResponseUtil.buildErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
//                errors
//        );
//    }
}
