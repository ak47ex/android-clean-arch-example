package com.suenara.exampleapp.domain.entity;

public class Dog {

    private final String title;
    private final String url;

    public Dog(String title, String url) {
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
