# 인증 시스템
> 개발 기간: 2022.12.05 - 2022.12.24
> 스마일게이트 윈터데브캠프 개인프로젝트
## 프로젝트 소개
- 회원 인증 시스템입니다.
## 기술 스택
### Front-End
- React.js
### Back-End
- Java 11
- SpringBoot 2.7.6
- SpringSecurity
- JWT
- MySQL
## 서비스 주요 기능
### 1. 로그인
![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/6db4f1cf-1bec-487c-8e1a-1abdac69485d/Untitled.png)
### 2. 회원가입
![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/44a52889-aaf7-45c8-81e2-ad57296154a1/Untitled.png)
### 3. 유저 리스트 조회
![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/39de81c4-e726-4a7c-bd66-b3313c2940da/Untitled.png)
## 질문
- spring 예외처리
```java
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
```

   
