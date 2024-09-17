package com.moneycat.core.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MoneyCatException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public MoneyCatException(HttpStatus httpStatus, ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.httpStatus = httpStatus;
        this.message = errorCode.getDefaultMessage();
    }

}