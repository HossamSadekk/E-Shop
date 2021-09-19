package com.example.ecommerceapp.models;

public class Category {
private String image_url;
private String name;
private String type;

    public Category() {
    }

    public Category(String image_url, String name, String type) {
        this.image_url = image_url;
        this.name = name;
        this.type = type;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
