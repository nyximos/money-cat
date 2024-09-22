package com.moneycat.core.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends MoneyCatException {

    public CategoryNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.CATEGORY_NOT_FOUND_EXCEPTION);
    }

    public CategoryNotFoundException(ErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }

    public CategoryNotFoundException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(httpStatus, errorCode);
    }

}