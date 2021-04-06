package com.deutsche.tradeStore.dto;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {
    int errorCode;
    String message;
    List<String> errors = new ArrayList<>();

    public ErrorResponse() {
    }

    public ErrorResponse(int errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
