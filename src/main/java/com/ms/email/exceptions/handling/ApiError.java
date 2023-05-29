package com.ms.email.exceptions.handling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ApiError {
    String path;
    String message;
    int statusCode;
    LocalDateTime localDateTime;
}