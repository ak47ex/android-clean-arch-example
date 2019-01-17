package com.suenara.exampleapp.data.exception;

import okhttp3.ResponseBody;

public class NetworkRequestException extends Exception {

    private final ResponseBody errorResponse;

    public NetworkRequestException(ResponseBody errorResponse) {
        super();
        this.errorResponse = errorResponse;
    }


    public ResponseBody getErrorResponse() {
        return errorResponse;
    }
}
