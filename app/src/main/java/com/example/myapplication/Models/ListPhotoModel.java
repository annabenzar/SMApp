package com.example.myapplication.Models;

import android.widget.ImageView;

import java.util.List;

public class ListPhotoModel {

    private String imageURL;

    public ListPhotoModel(String imageURL) {
        this.imageURL = imageURL;
    }
    public ListPhotoModel(){}

    public String getImageURL() {
        return imageURL;
    }
}
