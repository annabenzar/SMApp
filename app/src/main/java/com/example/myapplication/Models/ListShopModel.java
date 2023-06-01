package com.example.myapplication.Models;

public class ListShopModel {

    private String productName, productPrice, productCondition, productURL;

    public ListShopModel(String productName, String productPrice, String productCondition, String productURL) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCondition = productCondition;
        this.productURL = productURL;
    }
    public ListShopModel(){}

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductCondition() {
        return productCondition;
    }

    public String getProductURL() {
        return productURL;
    }
}
