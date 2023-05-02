package com.backend.master.errors;

import org.springframework.http.HttpStatus;

public class AuthoritationException extends RuntimeException{
    private HttpStatus status;
    private Integer errorCode;

    public AuthoritationException(final String message, final HttpStatus status) {
        super(message);
        this.status = status;
        this.errorCode = status.value();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
