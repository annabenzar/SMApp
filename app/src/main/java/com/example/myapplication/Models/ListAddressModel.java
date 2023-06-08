package com.example.myapplication.Models;

public class ListAddressModel {

    String userAddress;
    boolean isSelected;

    public ListAddressModel(){}
    public String getUserAddress(){
        return userAddress;
    }
    public void serUserAddress(String userAddress){
        this.userAddress=userAddress;
    }
    public boolean isSelected(){
        return  isSelected;
    }
    public void setSelected(boolean selected){
        isSelected =selected;
    }
}
