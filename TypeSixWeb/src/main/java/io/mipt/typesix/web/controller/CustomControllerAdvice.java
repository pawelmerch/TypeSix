package io.mipt.typesix.web.controller;

import io.mipt.typesix.businesslogic.service.core.exception.RegistrationServiceException;
import io.mipt.typesix.businesslogic.service.core.exception.RoleServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<String> registrationExceptionHandler(RegistrationServiceException registrationServiceException) {
        return ResponseEntity.badRequest().body(registrationServiceException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> roleServiceExceptionHandler(RoleServiceException roleServiceException) {
        return ResponseEntity.badRequest().body(roleServiceException.getMessage());
    }
}
