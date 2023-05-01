package com.ms.email.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionCodes {

    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),
    BAD_REQUEST_OWNER("400", "BAD REQUEST: Owner can't be empty"),
    BAD_REQUEST_EMAIL("400", "BAD REQUEST: Invalid Email"),
    BAD_REQUEST_MESSAGE("400", "BAD REQUEST: Message can't be empty"),
    BAD_REQUEST_SUBJECT("400", "BAD REQUEST: Subject can't be empty");

    private final String errorCode;
    private final String message;

    ExceptionCodes(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
