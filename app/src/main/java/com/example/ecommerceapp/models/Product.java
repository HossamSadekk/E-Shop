package com.example.ecommerceapp.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String description;
    private String image_url;
    private String rating;
    private int price;

    public Product() {
    }

    public Product(String name, String description, String image_url, String rating, int price) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.rating = rating;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getRating() {
        return rating;
    }

    public int getPrice() {
        return price;
    }
}
