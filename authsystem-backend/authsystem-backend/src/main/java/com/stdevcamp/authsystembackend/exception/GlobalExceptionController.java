package com.stdevcamp.authsystembackend.exception;

import com.stdevcamp.authsystembackend.exception.custom.BadRequestException;
import com.stdevcamp.authsystembackend.exception.custom.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestHandler(BadRequestException e) {
        log.error("BadRequestException 발생");
        e.printStackTrace();

        ErrorCode code = e.getErrorCode();
        return ResponseEntity.status(code.getHttpStatus())
                .body(getErrorResponse(code));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> nullPointerHandler(NotFoundException e) {
        log.error("NullPointerException 발생");
        e.printStackTrace();

        ErrorCode code = e.getErrorCode();
        return ResponseEntity.status(code.getHttpStatus())
                .body(getErrorResponse(code));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse typeMismatchHandler(MethodArgumentTypeMismatchException e) {
        log.error("요청 주소가 맞지 않습니다.");
        e.printStackTrace();

        return ErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .message("요청 주소가 맞지 않습니다.")
                .build();
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ErrorResponse dataDuplicateHandler(DuplicateKeyException e) {
        log.error("이미 존재하는 정보입니다.");
        e.printStackTrace();

        return ErrorResponse.builder()
                .message("이미 존재하는 정보입니다.")
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse PasswordMismatchHandler(BadCredentialsException e) {
        log.error("비밀번호가 맞지 않습니다.");
        e.printStackTrace();

        return ErrorResponse.builder()
                .message("비밀번호가 맞지 않습니다.")
                .build();
    }

    private ErrorResponse getErrorResponse(ErrorCode code) {
        return ErrorResponse.builder()
                .status(code.getHttpStatus().value())
                .errorCode(code.name())
                .message(code.getMessage())
                .build();
    }
}
