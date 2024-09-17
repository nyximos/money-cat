package com.moneycat.core.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
public class ResultResponse<T> implements Serializable {
    private boolean success;
    private HttpStatus status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResultResponse() {
        this.success = true;
        this.status = HttpStatus.OK;
        this.message = "success";
    }

    public ResultResponse(T data) {
        this.success = true;
        this.status = HttpStatus.OK;
        this.message = "Success";
        this.data = data;
    }

    public ResultResponse(boolean status, HttpStatus httpStatus, String message) {
        this.success = status;
        this.status = httpStatus;
        this.message = message;
    }

    public T getData() {
        return data;
    }
}
