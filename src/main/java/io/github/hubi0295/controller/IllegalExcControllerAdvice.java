package io.github.hubi0295.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = IllegalExceptionProcessing.class)
public class IllegalExcControllerAdvice {
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegaAgrumentEx(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegaStateEx(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());

    }
}
