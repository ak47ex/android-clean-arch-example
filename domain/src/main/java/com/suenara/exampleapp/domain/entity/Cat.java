package com.suenara.exampleapp.domain.entity;

public class Cat {

    private final String title;
    private final String url;

    public Cat(String title, String url) {
        super();
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
