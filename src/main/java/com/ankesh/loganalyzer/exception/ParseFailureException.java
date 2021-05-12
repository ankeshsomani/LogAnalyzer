package com.ankesh.loganalyzer.exception;

public class ParseFailureException extends Exception{
    private final String message;

    public ParseFailureException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
