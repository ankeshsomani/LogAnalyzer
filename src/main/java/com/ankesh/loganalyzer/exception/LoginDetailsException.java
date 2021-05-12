package com.ankesh.loganalyzer.exception;

public class LoginDetailsException extends Exception{
    private final String message;

    public LoginDetailsException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
