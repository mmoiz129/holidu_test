package com.holidu.interview.assignment.exception;

public class HoliduException extends RuntimeException {

    private String code;
    private String message;

    public HoliduException() {
        super();
    }

    public HoliduException(HoliduErrorType error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
