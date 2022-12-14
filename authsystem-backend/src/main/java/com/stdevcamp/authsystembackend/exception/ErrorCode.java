package com.stdevcamp.authsystembackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_PARAMETER(400, null, "Invalid Request Data"),
    NON_LOGIN(401,"C003","Non Login Status"),
    EXPIRED_TOKEN(401,"C004","Expired Token"),
    INVALID_TOKEN(401,"C005","Invalid Token");

    private final int status;
    private final String code;
    private final String message;

}
