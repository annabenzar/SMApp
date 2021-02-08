package com.example.myapplication.Models;

public class ListPhotoModel {

    private String imageURL,imageName,imageTime,imageType,imageIngredients,imagePrep,imageAuthor;

    public ListPhotoModel(String imageURL,String imageName,String imageTime,String imageType,String imageIngredients, String imagePrep,String imageAuthor) {
        this.imageURL = imageURL;
        this.imageName = imageName;
        this.imageTime = imageTime;
        this.imageType = imageType;
        this.imageIngredients=imageIngredients;
        this.imagePrep=imagePrep;
        this.imageAuthor=imageAuthor;
    }
    public ListPhotoModel(){}

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageTime() {
        return imageTime;
    }

    public void setImageTime(String imageTime) {
        this.imageTime = imageTime;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageIngredients() {
        return imageIngredients;
    }

    public void setImageIngredients(String imageIngredients) {
        this.imageIngredients = imageIngredients;
    }

    public String getImagePrep() {
        return imagePrep;
    }

    public void setImagePrep(String imagePrep) {
        this.imagePrep = imagePrep;
    }

    public String getImageAuthor() {
        return imageAuthor;
    }

    public void setImageAuthor(String imageAuthor) {
        this.imageAuthor = imageAuthor;
    }
}
