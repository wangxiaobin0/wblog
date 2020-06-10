package com.wblog.exception;

public class PolicyException extends RuntimeException {
    private Integer code;
    private String message;
    public PolicyException() {
        super();
    }

    public PolicyException(String message) {
        super(message);
    }
}
