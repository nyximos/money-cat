package com.moneycat.core.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends MoneyCatException {

    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_TOKEN);
    }

    public InvalidTokenException(ErrorCode errorCode) {
        super(HttpStatus.UNAUTHORIZED, errorCode);
    }

    public InvalidTokenException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(httpStatus, errorCode);
    }
}