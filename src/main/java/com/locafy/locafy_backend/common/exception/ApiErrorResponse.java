package com.locafy.locafy_backend.common.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {

    // Estructura comun para responder errores de forma consistente.
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    public ApiErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

}
