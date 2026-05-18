package com.locafy.locafy_backend.common.exception;

public class ResourceNotFoundException extends RuntimeException {

    // Se usa cuando un recurso solicitado no existe en la base.
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
