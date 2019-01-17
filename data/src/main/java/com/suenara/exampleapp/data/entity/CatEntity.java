package com.suenara.exampleapp.data.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CatEntity {

    @JsonProperty("url")
    private String imageUrl;

    @JsonProperty("title")
    private String title;

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
