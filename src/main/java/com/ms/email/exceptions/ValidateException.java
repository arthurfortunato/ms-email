package com.ms.email.exceptions;

import com.ms.email.models.ValidationError;
import lombok.Getter;

@Getter
public class ValidateException extends Exception {

    private final ValidationError error;

    public ValidateException(ValidationError error) {
        super(error.getMessage());
        this.error = error;
    }
}
