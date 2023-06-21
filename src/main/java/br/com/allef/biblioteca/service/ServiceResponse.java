package br.com.allef.biblioteca.service;

import org.springframework.http.HttpStatus;

public class ServiceResponse {
    private boolean success;
    private String message;

    private final HttpStatus httpStatus;


    public ServiceResponse(boolean success, String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.success = success;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }


}
