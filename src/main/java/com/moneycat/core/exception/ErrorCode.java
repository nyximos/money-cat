package com.moneycat.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND("해당 자원을 찾을 수 없습니다."),
    BAD_REQUEST("잘못된 요청입니다."),
    EMAIL_ALREADY_IN_USE("이미 사용중인 이메일입니다."),
    USER_NOT_FOUND_EXCEPTION("유저를 찾을 수 없습니다."),
    INVALID_PASSWORD("잘못된 비밀번호입니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    INVALID_REFRESH_TOKEN("유효하지 않은 리프레시 토큰입니다."),
    CATEGORY_NOT_FOUND_EXCEPTION("카테고리를 찾을 수 없습니다."),
    SPENDING_NOT_FOUND_EXCEPTION("지출 데이터를 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS_EXCEPTION("접근 권한이 없습니다."),
    ;

    private String defaultMessage;

}
