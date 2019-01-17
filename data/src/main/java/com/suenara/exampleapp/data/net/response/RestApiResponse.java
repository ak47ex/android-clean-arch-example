package com.suenara.exampleapp.data.net.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class RestApiResponse<T> {

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private List<T> data;

    public String getMessage() {
        return message;
    }

    public List<T> getData() {
        return data;
    }

}
