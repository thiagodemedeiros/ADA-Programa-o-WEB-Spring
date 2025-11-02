package com.ada.pokemon.exception;

// Exceção customizada para HTTP 404 [cite: 105, 106]
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}