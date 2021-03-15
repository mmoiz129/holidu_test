package com.holidu.interview.assignment.exception;

public enum HoliduErrorType {

    INVALID_RADIUS("001", "Please Enter radius greater then 0"),
    REQUIRED_PARAMETER("002", "Please provide required parameter");

    private final String code;
    private final String message;

    HoliduErrorType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() { return message; }

}
