package com.example.HelpDesk.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    // Formatted constructor: ResourceNotFoundException("Ticket", "id", 101L)
    // Produces: "Ticket not found with id: '101'"
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
