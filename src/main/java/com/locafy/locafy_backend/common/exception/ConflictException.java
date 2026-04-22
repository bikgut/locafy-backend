package com.locafy.locafy_backend.common.exception;

public class ConflictException extends RuntimeException {

    // Representa conflictos de negocio, por ejemplo una eliminacion no permitida.
    public ConflictException(String message) {
        super(message);
    }
}
