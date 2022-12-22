package com.stdevcamp.authsystembackend.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private final Integer status;
    private final String errorCode;
    private final String message;
}
