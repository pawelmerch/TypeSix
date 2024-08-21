package io.mipt.typesix.core.web.controller;

import io.mipt.typesix.businesslogic.service.core.RegistrationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Advice {
    @ExceptionHandler
    public ResponseEntity<String> errorHandler(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> registrationExceptionHandler(RegistrationException registrationException) {
        return ResponseEntity.badRequest().body(registrationException.getMessage());
    }
}
