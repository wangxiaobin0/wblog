package com.wblog.exception;

/**
 * 注册异常
 */
public class AuthException extends RuntimeException {
    private Integer code;
    private String message;
    public AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }
}
