package com.library.catalog_service.exception;

/**
 * Exception thrown when a resource with the same name already exists
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}





