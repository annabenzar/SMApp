package com.example.myapplication.Models;

public class ListRecipeModel {

    private String recipeURL, recipeName, recipeTime, recipeType, recipeIngredients, recipePrep, recipeAuthor;

    public ListRecipeModel(String recipeURL, String recipeName, String recipeTime, String recipeType, String recipeIngredients, String recipePrep, String recipeAuthor) {
        this.recipeURL = recipeURL;
        this.recipeName = recipeName;
        this.recipeTime = recipeTime;
        this.recipeType = recipeType;
        this.recipeIngredients = recipeIngredients;
        this.recipePrep = recipePrep;
        this.recipeAuthor = recipeAuthor;
    }
    public ListRecipeModel(){}

    public String getRecipeURL() {
        return recipeURL;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeTime() {
        return recipeTime;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public String getRecipePrep() {
        return recipePrep;
    }

    public String getRecipeAuthor() {
        return recipeAuthor;
    }
}
