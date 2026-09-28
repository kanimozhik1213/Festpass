
package com.example.Festpass.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(
            RuntimeException exception) {

        Map<String, String> error =
                Map.of("message", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}