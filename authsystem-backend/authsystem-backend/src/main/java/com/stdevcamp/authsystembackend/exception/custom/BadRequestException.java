package com.stdevcamp.authsystembackend.exception.custom;

import com.stdevcamp.authsystembackend.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BadRequestException extends RuntimeException {
    private final ErrorCode errorCode;
}
