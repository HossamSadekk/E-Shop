package com.example.ecommerceapp.models;


public class User {
    private String userID;
    private String userName;
    private String Email;
    private String userAddress;
    private String userPhoneNumber;
    private String userCity;



    public User() {
    }

    public User(String userID, String userName, String email, String userAddress, String userPhoneNumber, String userCity) {
        this.userID = userID;
        this.userName = userName;
        Email = email;
        this.userAddress = userAddress;
        this.userPhoneNumber = userPhoneNumber;
        this.userCity = userCity;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return Email;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public String getUserCity() {
        return userCity;
    }


}
