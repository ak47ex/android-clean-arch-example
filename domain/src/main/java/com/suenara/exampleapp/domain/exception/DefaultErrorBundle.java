package com.suenara.exampleapp.domain.exception;

public class DefaultErrorBundle implements ErrorBundle {

    private static final String UNKNOWN_ERROR = "Unknown error";

    private final Exception exception;

    public DefaultErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        return exception != null ? exception.getMessage() : UNKNOWN_ERROR;
    }
}
