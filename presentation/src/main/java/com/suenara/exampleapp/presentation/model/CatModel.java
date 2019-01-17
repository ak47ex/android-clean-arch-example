package com.suenara.exampleapp.presentation.model;

public class CatModel {

    private final String title;
    private final String url;

    public CatModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

}
