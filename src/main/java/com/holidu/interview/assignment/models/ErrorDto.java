package com.holidu.interview.assignment.models;

public class ErrorDto {

    private String errorCode;
    private String message;

    public ErrorDto(String message) {
        this.message = message;
    }

    public ErrorDto(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
