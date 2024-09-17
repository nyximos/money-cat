package com.moneycat.core.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyInUseException extends MoneyCatException {

    public EmailAlreadyInUseException() {
        super(HttpStatus.BAD_REQUEST, ErrorCode.EMAIL_ALREADY_IN_USE);
    }

    public EmailAlreadyInUseException(ErrorCode errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }

    public EmailAlreadyInUseException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(httpStatus, errorCode);
    }
}
