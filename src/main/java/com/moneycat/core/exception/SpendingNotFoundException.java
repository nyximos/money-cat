package com.moneycat.core.exception;

import org.springframework.http.HttpStatus;

public class SpendingNotFoundException extends MoneyCatException {

    public SpendingNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.SPENDING_NOT_FOUND_EXCEPTION);
    }

    public SpendingNotFoundException(ErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }

    public SpendingNotFoundException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(httpStatus, errorCode);
    }

}