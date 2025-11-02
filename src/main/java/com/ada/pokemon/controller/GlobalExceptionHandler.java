package com.ada.pokemon.controller;

import com.ada.pokemon.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Trata 404 da PokeAPI
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<Object> handlePokeApiNotFound(HttpClientErrorException.NotFound ex, WebRequest request) {
        return buildErrorResponse(
                "Pokémon não encontrado na PokeAPI.",
                HttpStatus.NOT_FOUND
        );
    }

    // Trata 404 do nosso banco H2 (idLocal)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Trata erros genéricos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
        return buildErrorResponse(
                "Ocorreu um erro interno: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        );
        return new ResponseEntity<>(body, status);
    }
}