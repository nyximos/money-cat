package com.moneycat.core.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends MoneyCatException {

    public UnauthorizedAccessException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED_ACCESS_EXCEPTION);
    }

    public UnauthorizedAccessException(ErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }

    public UnauthorizedAccessException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(httpStatus, errorCode);
    }

}