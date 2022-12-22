package com.stdevcamp.authsystembackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;
@Getter
@AllArgsConstructor
public enum JwtErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    REQUIRED_REQUSET_BODY(BAD_REQUEST, "올바른 JSON 형식이 아닙니다."),
    INPUT_TYPE_MISMATCH(BAD_REQUEST, "입력이 맞지 않습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(NOT_FOUND, "해당 제품 정보를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(NOT_FOUND, "해당 카테고리 정보를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(NOT_FOUND, "해당 리뷰 정보를 찾을 수 없습니다."),
    VIDEO_NOT_FOUND(NOT_FOUND, "해당 제품 영상을 찾을 수 없습니다."),


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다")

    INVALID_PARAMETER(, "Invalid Request Data"),
    NON_LOGIN(401,"C003","Non Login Status"),
    EXPIRED_TOKEN(401,"C004","Expired Token"),
    INVALID_TOKEN(401,"C005","Invalid Token")
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
