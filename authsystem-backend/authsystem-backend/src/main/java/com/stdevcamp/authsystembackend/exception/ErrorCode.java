package com.stdevcamp.authsystembackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;
@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    REQUIRED_REQUEST_BODY(BAD_REQUEST, "올바른 JSON 형식이 아닙니다."),
    INPUT_TYPE_MISMATCH(BAD_REQUEST, "입력이 맞지 않습니다."),
    INPUT_PASSWORD_MISMATCH(BAD_REQUEST, "입력이 맞지 않습니다."),
    /* 401 UNAUTHORIZED : 인증 실패  */
    NON_LOGIN(UNAUTHORIZED,"로그인이 필요합니다."),
    EXPIRED_TOKEN(UNAUTHORIZED,"토큰이 만료되었습니다."),
    INVALID_TOKEN(UNAUTHORIZED,"유효하지 않은 토큰입니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(NOT_FOUND, "해당 제품 정보를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(NOT_FOUND, "해당 카테고리 정보를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(NOT_FOUND, "해당 리뷰 정보를 찾을 수 없습니다."),
    VIDEO_NOT_FOUND(NOT_FOUND, "해당 제품 영상을 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다")
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
