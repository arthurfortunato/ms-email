package com.ms.email.models;

import com.ms.email.exceptions.ExceptionCodes;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ValidationError {

    private final String code;
    private final String message;
    private final LocalDateTime date;

    private ValidationError(String code, String message) {
        this.code = code;
        this.message = message;
        this.date = LocalDateTime.now();
    }

    public static ValidationError getFrom(ExceptionCodes exceptionCodes) {
        return new ValidationError(exceptionCodes.getErrorCode(), exceptionCodes.getMessage());
    }
}
