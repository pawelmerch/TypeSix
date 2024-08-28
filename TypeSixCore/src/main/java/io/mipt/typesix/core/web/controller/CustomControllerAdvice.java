package io.mipt.typesix.core.web.controller;

import io.mipt.typesix.businesslogic.service.core.exception.RegistrationException;
import io.mipt.typesix.businesslogic.service.core.exception.RoleServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<String> registrationExceptionHandler(RegistrationException registrationException) {
        return ResponseEntity.badRequest().body(registrationException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> roleServiceExceptionHandler(RoleServiceException roleServiceException) {
        return ResponseEntity.badRequest().body(roleServiceException.getMessage());
    }
}
