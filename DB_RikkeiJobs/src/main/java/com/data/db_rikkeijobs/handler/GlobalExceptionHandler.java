package com.data.db_rikkeijobs.handler;

import com.data.db_rikkeijobs.dto.response.ResponseWrapper;
import com.data.db_rikkeijobs.exception.HttpBadRequest;
import com.data.db_rikkeijobs.exception.HttpConflict;
import com.data.db_rikkeijobs.exception.HttpForbidden;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.exception.HttpUnauthorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> 
            errors.put(err.getField(), err.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseWrapper.builder()
                        .data(errors)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST)
                        .message("Validation failed")
                        .build()
        );
    }

    @ExceptionHandler(HttpBadRequest.class)
    public ResponseEntity<?> handleHttpBadRequest(HttpBadRequest ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseWrapper.builder()
                        .data(ex.getMessage())
                        .code(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(HttpNotFound.class)
    public ResponseEntity<?> handleHttpNotFound(HttpNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseWrapper.builder()
                        .data(ex.getMessage())
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(HttpConflict.class)
    public ResponseEntity<?> handleHttpConflict(HttpConflict ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ResponseWrapper.builder()
                        .data(ex.getMessage())
                        .code(HttpStatus.CONFLICT.value())
                        .status(HttpStatus.CONFLICT)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(HttpUnauthorized.class)
    public ResponseEntity<?> handleHttpUnauthorized(HttpUnauthorized ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ResponseWrapper.builder()
                        .data(ex.getMessage())
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .status(HttpStatus.UNAUTHORIZED)
                        .message(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(HttpForbidden.class)
    public ResponseEntity<?> handleHttpForbidden(HttpForbidden ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ResponseWrapper.builder()
                        .data(ex.getMessage())
                        .code(HttpStatus.FORBIDDEN.value())
                        .status(HttpStatus.FORBIDDEN)
                        .message(ex.getMessage())
                        .build()
        );
    }

    /**
     * Generic exception handler for all unexpected errors
     * This catches any exception not handled by specific handlers above
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        
        // Don't expose internal error details to client in production
        String message = "An unexpected error occurred. Please try again later.";
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ResponseWrapper.builder()
                        .data(message)
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(message)
                        .build()
        );
    }
}

