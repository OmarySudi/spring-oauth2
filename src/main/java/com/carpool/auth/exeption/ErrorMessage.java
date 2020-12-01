package com.carpool.auth.exeption;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMessage {

    private String message;
    private String details;
    private HttpStatus httpStatus;
    private LocalDateTime timestamp;

    public ErrorMessage(HttpStatus httpStatus, String message, String details) {
        this.message = message;
        this.details = details;
        this.httpStatus = httpStatus;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return timestamp;
    }
}
