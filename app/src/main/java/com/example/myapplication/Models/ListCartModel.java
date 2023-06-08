package com.example.myapplication.Models;

public class ListCartModel {

    private String productURL, productName, currentDate, currentTime, productTotalQuantity, productTotalPrice,firebaseUid;

    public ListCartModel(String productURL, String productName, String currentDate, String currentTime, String productTotalQuantity, String productTotalPrice, String firebaseUid) {
        this.productURL = productURL;
        this.productName = productName;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productTotalQuantity = productTotalQuantity;
        this.productTotalPrice = productTotalPrice;
        this.firebaseUid = firebaseUid;
    }
    public ListCartModel(){}

    public String getProductURL() {return productURL;}

    public String getProductName() {return productName;}
    public String getCurrentDate() { return currentDate;}
    public String getCurrentTime() { return currentTime;}
    public String getProductTotalQuantity() { return productTotalQuantity;}
    public String getProductTotalPrice() { return productTotalPrice;}
    public String getFirebaseUid() { return firebaseUid;}

}
