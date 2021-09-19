package com.example.ecommerceapp.models;

import java.io.Serializable;

public class Cart implements Serializable {
    private String currentTime;
    private String currentDate;
    private String productName;
    private int productPrice;
    private int totalQuantity;
    private int totalPrice;

    public Cart() {
    }

    public Cart(String currentTime, String currentDate, String productName, int productPrice, int totalQuantity, int totalPrice) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
