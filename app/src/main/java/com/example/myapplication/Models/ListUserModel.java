package com.example.myapplication.Models;

public class ListUserModel {

    private String nameUser, firstnameUser;

    public ListUserModel(String nameUser, String firstnameUser) {
        this.nameUser = nameUser;
        this.firstnameUser = firstnameUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getFirstnameUser() {
        return firstnameUser;
    }

}

