package com.moneycat.core.handler;

import com.moneycat.core.exception.MoneyCatException;
import com.moneycat.core.wrapper.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MoneyCatExceptionHandler {

    @ExceptionHandler(MoneyCatException.class)
    public ResultResponse<Void> handleWantedException(MoneyCatException ex) {
        log.warn("Gold Exception: {}", ex.getMessage());
        return new ResultResponse<>(false, ex.getHttpStatus(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultResponse<Void> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(x -> sb.append(x).append("\n"));
        log.warn("Validation Exception: {}", sb.toString().trim());
        return new ResultResponse<>(false, HttpStatus.BAD_REQUEST, sb.toString().trim());
    }
}